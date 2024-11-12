package com.cryptoemergency.cryptoemergency.http

import androidx.test.platform.app.InstrumentationRegistry
import com.cryptoemergency.cryptoemergency.api.http.ApiResponse
import com.cryptoemergency.cryptoemergency.api.http.httpClient
import com.cryptoemergency.cryptoemergency.api.http.createRequest
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.util.StringValues
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.junit.jupiter.api.Test

class SharedHttpTest {
    @Serializable
    data class LoginSuccess(
        val token: String,
    )

    @Serializable
    data class ErrorPlaceholder(
        val error: String,
    )

    @Serializable
    data class Headers(
        val host: String,
        val `x-request-start`: String,
        val connection: String,
        val `x-forwarded-proto`: String,
        val `x-forwarded-port`: String,
        val `x-amzn-trace-id`: String,
        val authorization: String,
        val accept: String,
        val `accept-charset`: String,
        val `user-agent`: String,
        val `content-type`: String,
        val `accept-encoding`: String
    )

    @Test
    fun loginSuccess() = runBlocking {
        @Serializable
        data class Request(
            val email: String,
            val password: String,
        )

        val res = request<LoginSuccess, ErrorPlaceholder>(
            path = "api/login",
            body = Request("eve.holt@reqres.in", "cityslicka")
        )

        assert(res is ApiResponse.Success)
        if (res is ApiResponse.Success) {
            assert(res.status == HttpStatusCode.OK)
            assert(res.body.token == "QpwL5tke4Pnpja7X4")
        }
    }

    @Test
    fun loginError() = runBlocking {
        @Serializable
        data class Request(
            val email: String
        )

        val res = request<LoginSuccess, ErrorPlaceholder>(
            path = "api/login",
            body = Request("ERROR@mail.ru")
        )

        assert(res is ApiResponse.Error)
        if (res is ApiResponse.Error) {
            assert(res.status == HttpStatusCode.BadRequest)
            assert(res.body?.error == "Missing password")
        }
    }

    @Test
    fun echoBody() = runBlocking {
        @Serializable
        data class Request(
            val message: String
        )

        @Serializable
        data class Response(
            val args: Map<String, String>,
            val data: Map<String, String>,
            val headers: Headers,
            val json: Map<String, String>,
            val url: String
        )

        val res = request<Response, ErrorPlaceholder>(
            method = HttpMethod.Post,
            host = "postman-echo.com",
            path = "post",
            body = Request("MESSSAGES")
        )

        assert(res is ApiResponse.Success)
        if (res is ApiResponse.Success) {
            assert(res.status == HttpStatusCode.OK)
            assert(res.body.data["message"] == "MESSSAGES")
        }
    }

    @Test
    fun echoParams() = runBlocking {
        @Serializable
        data class Response(
            val args: Map<String, String>,
            val headers: Headers,
            val url: String
        )

        val res = request<Response, ErrorPlaceholder>(
            method = HttpMethod.Get,
            host = "postman-echo.com",
            path = "get",
            params = StringValues.build {
                append("key1", "value1")
                append("key2", "value2")
            }
        )

        assert(res is ApiResponse.Success)
        if (res is ApiResponse.Success) {
            assert(res.status == HttpStatusCode.OK)
            assert(res.body.args["key1"] == "value1")
            assert(res.body.args["key2"] == "value2")
        }
    }

    @Test
    fun echoHeader() = runBlocking {
        @Serializable
        data class EchoResponse(
            val args: Map<String, String>,
            val headers: Headers,
            val url: String
        )

        val token = "Bearer TOKBFYGIAWSBDJY@QG#B@UDSAYSDAYU@@!#"

        val res = request<EchoResponse, ErrorPlaceholder>(
            method = HttpMethod.Get,
            host = "postman-echo.com",
            path = "get",
            overrideToken = token
        )

        assert(res is ApiResponse.Success)
        if (res is ApiResponse.Success) {
            assert(res.status == HttpStatusCode.OK)
            assert(res.body.headers.authorization == token)
        }
    }

    private suspend inline fun <reified Success, reified Error>request(
        path: String,
        host: String = "reqres.in",
        body: @Serializable Any? = null,
        params: StringValues = StringValues.Empty,
        method: HttpMethod = HttpMethod.Post,
        overrideToken: String? = null,
    ) = httpClient.createRequest<Success, Error>(
        method = method,
        protocol = URLProtocol.HTTPS,
        host = host,
        overrideToken = overrideToken,
        params = params,
        path = path,
        port = 443,
        body = body,
        context = InstrumentationRegistry.getInstrumentation().targetContext,
    )
}
