package com.cryptoemergency.cryptoemergency.lib

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

object Permissions {
    /**
     * Запрос на [Manifest.permission.READ_MEDIA_IMAGES] или [Manifest.permission.READ_EXTERNAL_STORAGE]
     * в зависимости от уровня API
     * */
    @Composable
    fun Images(
        onPermissionResult: (Boolean) -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Request(
                Manifest.permission.READ_MEDIA_IMAGES,
                onPermissionResult,
            )
        } else {
            Request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                onPermissionResult,
            )
        }
    }

    /**
     * Запрос на [Manifest.permission.READ_MEDIA_VIDEO] или [Manifest.permission.READ_EXTERNAL_STORAGE]
     * в зависимости от уровня API
     * */
    @Composable
    fun Video(
        onPermissionResult: (Boolean) -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Request(
                Manifest.permission.READ_MEDIA_VIDEO,
                onPermissionResult,
            )

        } else {
            Request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                onPermissionResult,
            )
        }
    }

    /**
     * Запрос на [Manifest.permission.CAMERA]
     * */
    @Composable
    fun Camera(
        onPermissionResult: (Boolean) -> Unit
    ) {
        Request(
            Manifest.permission.CAMERA,
            onPermissionResult,
        )
    }

    /**
     * Запрос на [Manifest.permission.ACCESS_COARSE_LOCATION] и [Manifest.permission.ACCESS_FINE_LOCATION]
     * */
    @Composable
    fun Geolocation(
        onPermissionResult: (Map<String, Boolean>) -> Unit
    ) {
        Request(
            listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            onPermissionResult,
        )
    }

    /**
     * Запрос на приблизительное местоположение [Manifest.permission.ACCESS_COARSE_LOCATION]
     * */
    @Composable
    fun CoarseLocation(
        onPermissionResult: (Boolean) -> Unit
    ) {
        Request(
           Manifest.permission.ACCESS_COARSE_LOCATION,
            onPermissionResult,
        )
    }

    /**
    * Запрос на точное местоположение [Manifest.permission.ACCESS_FINE_LOCATION]
    * */
    @Composable
    fun FineLocation(
        onPermissionResult: (Boolean) -> Unit
    ) {
        Request(
            Manifest.permission.ACCESS_FINE_LOCATION,
            onPermissionResult,
        )
    }

    /**
     * Обертка над запросом разрешения для нескольких разрешений
     */
    @Composable
    @OptIn(ExperimentalPermissionsApi::class)
    private fun Request(
        permissions: List<String>,
        onPermissionsResult: (Map<String, Boolean>) -> Unit = {}
    ) {
        val states = rememberMultiplePermissionsState(
            permissions,
            onPermissionsResult,
        )

        SideEffect {
            LaunchPermissionRequest(states.permissions)
        }
    }

    /**
     * Обертка над запросом разрешения для одиночного разрешения
     */
    @Composable
    @OptIn(ExperimentalPermissionsApi::class)
    private fun Request(
        permissions: String,
        onPermissionsResult: (Boolean) -> Unit = {}
    ) {
        val state = rememberPermissionState(
            permissions,
            onPermissionsResult,
        )

        SideEffect {
            LaunchPermissionRequest(listOf(state))
        }
    }

    /**
     * Запрос на разрешение, если его нет. Вызывать только вне [Composable]
     *
     * @param states Обьект состояния разрешения
     *
     * @sample SampleGetOnSideEffect
     * @sample SampleGetOnButton
     * */
    @OptIn(ExperimentalPermissionsApi::class)
    fun LaunchPermissionRequest(
        states: List<PermissionState>,
        onResult: (Map<String, Boolean>) -> Unit = {}
    ) {
        val result = mutableMapOf<String, Boolean>()

        states.forEach {
            if (it.status.isGranted.not()) {
                it.launchPermissionRequest()
            }

            result[it.permission] = it.status.isGranted
        }

        onResult(result)
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun SampleGetOnSideEffect() {
    val hasPermission = remember { mutableStateOf(false) }

    val state = rememberPermissionState(
        Manifest.permission.CAMERA,
    ) { hasPermission.value = it }

    SideEffect {
        Permissions.LaunchPermissionRequest(listOf(state))
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun SampleGetOnButton() {
    val hasPermission = remember { mutableStateOf(false) }

    val states = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ),
    ) {
        hasPermission.value = it[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                it[Manifest.permission.ACCESS_COARSE_LOCATION] == true
    }

    Button(
        { Permissions.LaunchPermissionRequest(states.permissions) }
    ) {
        Text("Get permission")
    }
}
