package com.cryptoemergency.cryptoemergency.api.http

import com.cryptoemergency.cryptoemergency.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val httpClient by lazy {
    HttpClient(OkHttp) {
        install(Logging) {
            level = if(BuildConfig.DEBUG) {
                LogLevel.ALL
            } else {
                LogLevel.NONE
            }
        }
        install(ContentNegotiation) {
            json(json)
        }
        install(HttpTimeout) {
            connectTimeoutMillis = 10_000L
            requestTimeoutMillis = 10_000L
            socketTimeoutMillis = 10_000L
        }
    }
}

val json by lazy {
    Json {
        isLenient = true
        ignoreUnknownKeys = true
    }
}
