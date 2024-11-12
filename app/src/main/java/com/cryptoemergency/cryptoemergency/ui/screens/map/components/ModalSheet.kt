package com.cryptoemergency.cryptoemergency.ui.screens.map.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cryptoemergency.cryptoemergency.providers.localLocation.LocalLocation
import com.cryptoemergency.cryptoemergency.ui.screens.map.MapViewModel
import org.osmdroid.util.GeoPoint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalSheet(vm: MapViewModel) {
    val location = LocalLocation.current.location.value

    if (vm.showModal.value) {
        ModalBottomSheet(
            sheetState = rememberModalBottomSheetState(),
            onDismissRequest = {
                vm.showModal.value = false
            }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                vm.selectedMarker.value?.title?.let {
                    TextField(
                        value = it.value,
                        onValueChange = {
                                newText -> it.value = newText
                            vm.renameMark(newText)
                        },
                        label = { Text("Имя точки") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                Spacer(Modifier.height(70.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        location?.let {
                            vm.setRoute(
                                listOf(
                                    GeoPoint(it.latitude, it.longitude),
                                    vm.selectedMarker.value!!.geoPoint,
                                )
                            )
                        }
                    },
                ) {
                    Text("Построить маршрут")
                }

                Spacer(Modifier.height(7.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Color.Red
                    ),
                    onClick = {
                        vm.deleteMarker()
                    },
                ) {
                    Text("Удалить метку")
                }

                Spacer(Modifier.height(10.dp))
            }
        }
    }
}
