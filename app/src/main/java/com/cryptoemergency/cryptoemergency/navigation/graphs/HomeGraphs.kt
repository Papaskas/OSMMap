package com.cryptoemergency.cryptoemergency.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cryptoemergency.cryptoemergency.navigation.Destination
import com.cryptoemergency.cryptoemergency.ui.screens.map.MapScreen

fun NavGraphBuilder.homeGraphs() {
    composable<Destination.Home.Map> { MapScreen() }
}
