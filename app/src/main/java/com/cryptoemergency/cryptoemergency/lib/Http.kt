package com.cryptoemergency.cryptoemergency.lib

import android.content.Context
import android.util.Log
import com.cryptoemergency.cryptoemergency.BuildConfig
import com.cryptoemergency.cryptoemergency.api.http.IOException
import com.cryptoemergency.cryptoemergency.api.http.SerializationException
import com.cryptoemergency.cryptoemergency.api.http.UnknownHostException
import io.ktor.http.HttpStatusCode

object Http {
    fun getDefaultMessages(context: Context, statusCode: HttpStatusCode): String {
        Log.e(
            "Default error message",
            "Error code: ${ statusCode.value },\n description: ${ statusCode.description }",
        )

        return when (statusCode.value) {
            HttpStatusCode.SerializationException.value -> {
                if (BuildConfig.DEBUG) {
                    "R.string.error__serialization_exception"
                } else {
                    "R.string.error__internal_server"
                }
            }
            HttpStatusCode.UnknownHostException.value -> "R.string.error__unknown_host_exception"
            HttpStatusCode.IOException.value -> "R.string.error__io_exception"
            HttpStatusCode.Forbidden.value -> "R.string.error__forbidden"
            HttpStatusCode.MethodNotAllowed.value -> "context.getString(R.string.error__method_not_allowed)"
            HttpStatusCode.TooManyRequests.value -> "context.getString(R.string.error__too_many_request)"
            HttpStatusCode.RequestTimeout.value -> "context.getString(R.string.error__request_timeout)"
            HttpStatusCode.InternalServerError.value -> "context.getString(R.string.error__internal_server)"
            else -> {
                if (BuildConfig.DEBUG) {
                    "context.getString(R.string.error__internal_client)"
                } else {
                    "context.getString(R.string.error__internal_server)"
                }
            }
        }
    }

}
