package com.cryptoemergency.cryptoemergency

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.cryptoemergency.cryptoemergency.lib.Permissions
import com.cryptoemergency.cryptoemergency.navigation.Navigation
import com.cryptoemergency.cryptoemergency.providers.localLocation.LocalLocationProvider
import com.cryptoemergency.cryptoemergency.providers.localNavController.NavControllerProvider
import com.cryptoemergency.cryptoemergency.providers.localSnackBar.SnackBarProvider
import com.cryptoemergency.cryptoemergency.ui.screens.noPermission.NoPermissionScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MainApp()
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun MainApp() {
        val state = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

        if (state.status.isGranted) {
            NavControllerProvider {
                SnackBarProvider {
                    LocalLocationProvider(application) {
                        Navigation()
                    }
                }
            }
        } else {
            NoPermissionScreen {
                Permissions.LaunchPermissionRequest(listOf(state))
            }
        }
    }
}
