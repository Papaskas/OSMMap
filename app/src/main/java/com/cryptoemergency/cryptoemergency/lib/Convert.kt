package com.cryptoemergency.cryptoemergency.lib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Convert {
    /**
     * Функция преобразовывающая UnixTime в дату по паттерну
     * */
    fun Long.millisToDate(
        pattern: String = "MM/dd/yyyy"
    ): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(Date(this))
    }

    /**
     * Функция преобразовывающая Uri
     *
     * @param format Формат данных - jpeg, png
     * @param quality Качество сжатия изображение
     * */
    @Deprecated("Don't use it")
    fun Uri.toBase64(
        context: Context,
        format: Bitmap.CompressFormat,
        quality: Int = 100,
    ): String {
        val inputStream = context.contentResolver.openInputStream(this)
        val bitmap = BitmapFactory.decodeStream(inputStream)

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(format, quality, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        val prefix = when (format) {
            Bitmap.CompressFormat.JPEG -> "data:image/jpeg;base64,"
            Bitmap.CompressFormat.PNG -> "data:image/png;base64,"
            else -> "data:image/jpeg;base64,"
        }

        return prefix + Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    /**
     * Функция ИЗОБРАЖЕНИЕ Uri в bitmap
     * */
    fun Uri.toBitmap(context: Context): Bitmap {
        return if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media.getBitmap(context.contentResolver, this)
        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, this)
            ImageDecoder.decodeBitmap(source)
        }
    }
}
