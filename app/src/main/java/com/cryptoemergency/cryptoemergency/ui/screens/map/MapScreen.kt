package com.cryptoemergency.cryptoemergency.ui.screens.map

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.cryptoemergency.cryptoemergency.ui.screens.map.components.ModalSheet
import com.cryptoemergency.cryptoemergency.ui.screens.map.components.OSMMap

@Composable
fun MapScreen(
    vm: MapViewModel = hiltViewModel(),
) {
    Column {
        OSMMap(vm)
        ModalSheet(vm)
    }
}
