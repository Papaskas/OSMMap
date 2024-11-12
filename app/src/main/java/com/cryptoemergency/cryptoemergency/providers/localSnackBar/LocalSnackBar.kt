package com.cryptoemergency.cryptoemergency.providers.localSnackBar

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackbar =
    staticCompositionLocalOf<SnackbarHostState> { error("No localSnackBar provided") }

@Composable
fun SnackBarProvider(content: @Composable () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }

    CompositionLocalProvider(LocalSnackbar provides snackbarHostState) {
        content()
    }
}
