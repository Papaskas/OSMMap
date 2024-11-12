package com.cryptoemergency.cryptoemergency.api.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.cryptoemergency.cryptoemergency.repository.store.ProtoKeys
import kotlinx.coroutines.flow.first

/**
 * Класс для хранения и извлечения данных с использованием хранилища данных
 *
 * @param key Ключ для идентификации данных в хранилище данных
 * @param context контекст приложения
 *
 * @constructor Создает новый экземпляр хранилища
 */
class ProtoStore<T>(
    private val key: ProtoKeys<T>,
    private val context: Context,
) {
    private val Context.dataStore: DataStore<T> by dataStore(
        fileName = key.toString(),
        serializer = key.serializer,
    )

    /*
    *
    * Прямой доступ к базе
    *
    * */
    val dataStore: DataStore<T> = context.dataStore

    /**
     * Извлекает сохраненные данные, связанные с данным ключом
     *
     * @return Возвращает сохраненные данные типа T.
     */
    suspend fun get(): T = dataStore.data.first()

    /**
     * Сохраняет заданные данные, связанные с заданным ключом.
     *
     * @param value Значение данных, которые будут сохранены.
     */
    suspend fun put(value: T) {
        dataStore.updateData { value }
    }
}
