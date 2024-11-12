package com.cryptoemergency.cryptoemergency.repository.store

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Представление в виде ключей, используемые для хранения и извлечения данных в хранилище.
 *
 * Используется библиотека DataStore
 *
 * @param T Тип значения, связанного с ключом, передается вместе с ключом,
 * типизиурет ответы из хранилища
 *
 * @param key Настройки связанные с ключом.
 * @param defaultValue Значение по умолчанию, которое будет использоваться, если ключ не найден в
 * хранилище настроек
 *
 * @see androidx.datastore.preferences.core.Preferences.Key
 */
sealed class Keys<T>(
    val key: Preferences.Key<T>,
    val defaultValue: T,
) {
    data object TOKEN : Keys<String>(
        key = stringPreferencesKey("TOKEN"),
        defaultValue = "",
    )

    data object PinCode : Keys<String>(
        key = stringPreferencesKey("pin-code"),
        defaultValue = "",
    )
}
