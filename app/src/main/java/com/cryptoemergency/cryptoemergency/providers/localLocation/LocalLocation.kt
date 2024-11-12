package com.cryptoemergency.cryptoemergency.providers.localLocation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

val LocalLocation = staticCompositionLocalOf<LocationProvider> {
    error("No LocationProvider provided")
}

@Composable
fun LocalLocationProvider(application: Application, content: @Composable () -> Unit) {
    val locationProvider = remember { LocationProvider(application) }

    CompositionLocalProvider(LocalLocation provides locationProvider) {
        content()
    }
}
