package com.cryptoemergency.cryptoemergency.lib

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

object Media {

    /**
     * @param onPermissionResult Will be called with whether or not the user granted the permission after
     * PermissionState. launchPermissionRequest is called.
     * @param sortOrder Параметр сортировки. По умолчанию по возрастанию
     * */
    @Composable
    fun getImages(
        onPermissionResult: (Boolean) -> Unit = {},
        sortOrder: String? = "${MediaStore.Images.Media.DATE_MODIFIED} DESC",
    ): State<List<Uri>> {
        val context = LocalContext.current
        val mediaFiles = remember { mutableStateOf<List<Uri>>(emptyList()) }

        var hasPermission by remember { mutableStateOf(false) }

        Permissions.Images { granted ->
            hasPermission = granted
            onPermissionResult(granted)
        }

        LaunchedEffect(hasPermission) {
            val projection = arrayOf(MediaStore.Images.Media._ID)

            val cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder,
            )

            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val uris = mutableListOf<Uri>()
                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    uris.add(contentUri)
                }
                mediaFiles.value = uris
            }
        }

        return mediaFiles
    }
}
