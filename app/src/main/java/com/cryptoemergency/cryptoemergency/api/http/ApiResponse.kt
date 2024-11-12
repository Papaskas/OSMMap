package com.cryptoemergency.cryptoemergency.api.http

import io.ktor.http.Headers
import io.ktor.http.HttpStatusCode

sealed interface ApiResponse<Success, Error> {
    data class Success<Success>(
        val status: HttpStatusCode,
        val body: Success,
        val headers: Headers,
    ) : ApiResponse<Success, Nothing>

    data class Error<Error>(
        val status: HttpStatusCode,
        val body: Error? = null,
        val headers: Headers? = null,
    ) : ApiResponse<Nothing, Error>
}
