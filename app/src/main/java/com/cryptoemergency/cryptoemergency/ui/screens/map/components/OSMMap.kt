package com.cryptoemergency.cryptoemergency.ui.screens.map.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.cryptoemergency.cryptoemergency.providers.localLocation.LocalLocation
import com.cryptoemergency.cryptoemergency.ui.screens.map.MapViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay

@Composable
fun OSMMap(vm: MapViewModel) {
    val location = LocalLocation.current.location
    val context = LocalContext.current

    Configuration.getInstance().userAgentValue = "com.testwork.geoapp"

    location.value?.let { currentLocation ->
        AndroidView(factory = {
            MapView(context).apply {
                vm.mapView = this
                vm.initMapSettings()
                vm.loadSavedMarkers()

                vm.addMarkCurrentPosition(currentLocation)
                controller.setCenter(GeoPoint(currentLocation.latitude, currentLocation.longitude))

                // Обработка событий на карте
                val mapEventsReceiver = object : MapEventsReceiver {
                    override fun singleTapConfirmedHelper(p: GeoPoint?) = false

                    override fun longPressHelper(geoPoint: GeoPoint?): Boolean {
                        geoPoint?.let {
                            vm.addMarker(it)
                        }

                        return true
                    }
                }

                val mapEventsOverlay = MapEventsOverlay(mapEventsReceiver)
                overlays.add(mapEventsOverlay)
            }
        }, update = { mapView ->
            mapView.onResume()
        })
    } ?: run {
        AwaitLocation()
    }
}

@Composable
private fun AwaitLocation() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
        Spacer(Modifier.height(15.dp))
        Text("Получение геопозиции телефона")
    }
}
