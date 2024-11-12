package com.cryptoemergency.cryptoemergency.repository.store

import android.util.Log
import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class GenericSerializer<T>(
    private val serializer: KSerializer<T>,
    override val defaultValue: T,
) : Serializer<T> {
    override suspend fun readFrom(input: InputStream): T =
        try {
            Json.decodeFromString(
                deserializer = serializer,
                string = input.readBytes().decodeToString(),
            )
        } catch (e: SerializationException) {
            Log.e("GenericSerializer", e.message ?: "GenericSerializer")
            defaultValue
        }

    override suspend fun writeTo(
        t: T,
        output: OutputStream,
    ) {
        withContext(Dispatchers.IO) {
            output.write(
                Json
                    .encodeToString(
                        serializer = serializer,
                        value = t,
                    ).encodeToByteArray(),
            )
        }
    }
}
