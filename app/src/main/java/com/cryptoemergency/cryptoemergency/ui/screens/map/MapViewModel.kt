package com.cryptoemergency.cryptoemergency.ui.screens.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cryptoemergency.cryptoemergency.api.http.ApiResponse
import com.cryptoemergency.cryptoemergency.api.store.ProtoStore
import com.cryptoemergency.cryptoemergency.repository.requests.getRoute.getRouteRequest
import com.cryptoemergency.cryptoemergency.repository.store.data.Mark
import com.cryptoemergency.cryptoemergency.repository.store.data.Markers
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Marker.ANCHOR_BOTTOM
import org.osmdroid.views.overlay.Marker.ANCHOR_CENTER
import org.osmdroid.views.overlay.Polyline
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val markersStore: ProtoStore<Markers>,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    val showModal = mutableStateOf(false)

    private val markers = mutableStateListOf<CustomMarker>()
    val selectedMarker = mutableStateOf<CustomMarker?>(null)

    @SuppressLint("StaticFieldLeak")
    lateinit var mapView: MapView

    private var currentRoute: Polyline? = null

    fun loadSavedMarkers() {
        viewModelScope.launch {
            val res = markersStore.get().markers

            withContext(Dispatchers.Main) {

                res.forEach {
                    addMarker(
                        geoPoint = GeoPoint(it.latitude, it.longitude),
                        title = it.title,
                        isLoadingFromStorage = true
                    )
                }
            }
        }
    }

    fun initMapSettings() {
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(17.0)
    }

    fun setRoute(pointsRoute: List<GeoPoint>) {
        viewModelScope.launch {
            val res = getRouteRequest(
                context = context,
                pointsRoute
            )

            if(res is ApiResponse.Success) {
                if(res.body.routes.isEmpty()) return@launch

                val route = res.body.routes[0]
                val coordinates = route.geometry.coordinates

                val points = coordinates.map { GeoPoint(it[1], it[0]) }

                withContext(Dispatchers.Main) {
                    currentRoute?.let {
                        mapView.overlays.remove(it)
                    }

                    val line = Polyline()
                    line.setPoints(points)
                    mapView.overlays.add(line)
                    currentRoute = line
                    mapView.invalidate()
                }
            }
        }
    }

    fun deleteMarker() {
        selectedMarker.value?.geoPoint?.let { geoPoint ->
            val osmMarker = mapView.overlays.filterIsInstance<Marker>().find {
                it.position == geoPoint
            }

            if (osmMarker != null) {
                mapView.overlays.remove(osmMarker)
                mapView.invalidate()
                markers.removeIf { it.geoPoint == geoPoint }
            }

            showModal.value = false

            viewModelScope.launch {
                val currentMarkers = markersStore.get()
                val updatedMarkers = currentMarkers.copy(markers = currentMarkers.markers.filterNot {
                    it.latitude == geoPoint.latitude && it.longitude == geoPoint.longitude
                })
                markersStore.put(updatedMarkers)
            }
        }
    }

    fun addMarker(
        geoPoint: GeoPoint,
        title: String = "Точка на карте",
        isLoadingFromStorage: Boolean = false,
    ) {
        val marker = Marker(mapView)

        markers.add(CustomMarker(geoPoint, mutableStateOf(title)))

        viewModelScope.launch {
            val currentMarkers = markersStore.get()
            val updatedMarkers = currentMarkers.copy(markers = currentMarkers.markers + Mark(
                longitude = geoPoint.longitude,
                latitude = geoPoint.latitude,
                title = title,
            ))

            markersStore.put(updatedMarkers)
        }

        marker.position = geoPoint
        marker.setAnchor(ANCHOR_CENTER, ANCHOR_BOTTOM)
        marker.title = title

        marker.setOnMarkerClickListener { _, _ ->
            selectedMarker.value = markers.find { marker ->
                marker.geoPoint == geoPoint
            }
            showModal.value = true

            true
        }

        mapView.overlays.add(marker)
        mapView.invalidate()


        if (isLoadingFromStorage) return
        selectedMarker.value = markers.find {
            it.geoPoint == geoPoint
        }
        showModal.value = true
    }

    fun addMarkCurrentPosition(location: Location) {
        val startPoint = GeoPoint(location.latitude, location.longitude)
        val startMarker = Marker(mapView)
        startMarker.position = startPoint
        startMarker.setAnchor(ANCHOR_CENTER, ANCHOR_BOTTOM)
        startMarker.title = "Ваше местоположение"
        mapView.overlays.add(startMarker)
    }

    fun renameMark(value: String) {
        selectedMarker.value?.let { selectMark ->
            viewModelScope.launch {
                val currentMarkers = markersStore.get()

                val updatedMarkers = currentMarkers.copy(
                    markers = currentMarkers.markers.map {
                        if (
                            it.longitude == selectMark.longitude &&
                            it.latitude == selectMark.latitude
                        ) {
                            it.copy(title = value)
                        } else it
                    }
                )

                markersStore.put(updatedMarkers)
            }
        }
    }

    suspend fun getAddress(geoPoint: GeoPoint): String {
        return withContext(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(getApplication(context), Locale.getDefault())
                val addresses: List<Address>? = geocoder.getFromLocation(geoPoint.latitude, geoPoint.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    address.getAddressLine(0)
                } else {
                    "Address not found"
                }
            } catch (e: Exception) {
                "Unable to get address: ${e.message}"
            }
        }
    }
}

data class CustomMarker(
    val geoPoint: GeoPoint,
    val title: MutableState<String>,
    val longitude: Double = geoPoint.longitude,
    val latitude: Double = geoPoint.latitude,
)
