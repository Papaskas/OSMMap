package com.cryptoemergency.cryptoemergency.api.http

import io.ktor.http.HttpStatusCode

val HttpStatusCode.Companion.SerializationException: HttpStatusCode
    get() = HttpStatusCode(SERIALIZATION_EXCEPTION_CODE, "Serialization exception")

val HttpStatusCode.Companion.IOException: HttpStatusCode
    get() = HttpStatusCode(IO_EXCEPTION_CODE, "IO exception")

val HttpStatusCode.Companion.UnknownHostException: HttpStatusCode
    get() = HttpStatusCode(UNKNOWN_HOST_EXCEPTION_CODE, "Unknown host exception")

private const val SERIALIZATION_EXCEPTION_CODE = -1
private const val IO_EXCEPTION_CODE = -900
private const val UNKNOWN_HOST_EXCEPTION_CODE = -1000
