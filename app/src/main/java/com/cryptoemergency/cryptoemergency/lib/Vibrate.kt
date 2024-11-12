package com.cryptoemergency.cryptoemergency.lib

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat

/**
 * Вызов вибрации устройства
 *
 * @param context контекст приложения
 * @param milliseconds длительность вибрации в миллисекундах
 * @param amplitude Сила вибрации. Это должно быть значение от 1 до 255
 * */
fun vibrate(
    context: Context,
    milliseconds: Long = 100,
    amplitude: Int = VibrationEffect.DEFAULT_AMPLITUDE,
) {
    val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java)

    vibrator?.let {
        if (it.hasVibrator()) {
            val effect =
                VibrationEffect.createOneShot(
                    milliseconds,
                    amplitude,
                )
            it.vibrate(effect)
        }
    }
}
