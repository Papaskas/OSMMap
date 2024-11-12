package com.cryptoemergency.cryptoemergency.repository.store

import androidx.datastore.core.Serializer
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.cryptoemergency.cryptoemergency.repository.store.data.CurrentTheme
import com.cryptoemergency.cryptoemergency.repository.store.data.Markers
import com.cryptoemergency.cryptoemergency.repository.store.data.User
import kotlinx.serialization.serializer

/**
 * Представление в виде ключей, используемые для хранения и извлечения данных в хранилище настроек.
 * Каждый ключ связан с определенным типом данных, значением по умолчанию и сериализатором.
 *
 * Используется библиотека Proto DataStore
 *
 * @param T Тип значения, связанного с ключом, передается вместе с ключом,
 * типизиурет ответы из хранилища
 *
 * @param key Настройки связанные с ключом.
 *
 * @param defaultValue Значение по умолчанию, которое будет использоваться, если ключ не найден в
 * хранилище настроек
 *
 * @param serializer Сериализатор, используемый для сериализации и десериализации данных.
 */
sealed class ProtoKeys<T>(
    val key: Preferences.Key<String>,
    val defaultValue: T,
    val serializer: Serializer<T>,
) {
    data object USER : ProtoKeys<User>(
        key = stringPreferencesKey("user"),
        defaultValue = User(),
        serializer = GenericSerializer(serializer<User>(), User()),
    )

    data object THEME : ProtoKeys<CurrentTheme>(
        key = stringPreferencesKey("theme"),
        defaultValue = CurrentTheme.NULL,
        serializer = GenericSerializer(serializer<CurrentTheme>(), CurrentTheme.NULL),
    )

    data object MARKERS : ProtoKeys<Markers>(
        key = stringPreferencesKey("markers"),
        defaultValue = Markers(emptyList()),
        serializer = GenericSerializer(serializer<Markers>(), Markers(emptyList())),
    )
}
