package com.cryptoemergency.cryptoemergency.api.http

import android.content.Context
import android.util.Log
import com.cryptoemergency.cryptoemergency.BuildConfig
import com.cryptoemergency.cryptoemergency.api.store.Store
import com.cryptoemergency.cryptoemergency.repository.store.Keys
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.util.StringValues
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import okio.IOException
import java.net.UnknownHostException

/**
 * Безопасная и универсальная функция HTTP-запроса, которая обрабатывает различные сценарии,
 * такие как исключения при сериализации, исключения при ответе сервера и проблемы с сетевым подключением.
 * Выполняется в IO потоке
 *
 * @param path Путь к запрашиваемой конечной точке API.
 * @param context Контекст, необходимый для извлечения токена из внутреннего хранилища.
 * @param host Хост сервера API. Значение по умолчанию получено из BuildConfig.HOST.
 * @param protocol Протокол, который будет использоваться для подключения по URL. По умолчанию берется из BuildConfig.
 * @param port Номер порта, который будет использоваться для подключения по URL. По умолчанию берется из BuildConfig.
 * Если нужно использовать обычный Http Https, то порт ставить 80 443 соответсвенно
 * @param params Определяет параметры, которые будут добавлены к URL-адресу. Значением по
 * умолчанию является пустая строка значений.
 * @param method HTTP-метод, который будет использоваться для запроса. Значение по умолчанию - HttpMethod.Get.
 * @param body Тело запроса в формате JSON. Принимаемый ти - @Serializable data class.
 * Значение по умолчанию - null.
 * @param overrideToken Токен, который должен быть включен в заголовок запроса. Если значение равно null,
 * функция извлечет токен из контекста.
 *
 * @return [ApiResponse.Success] или [ApiResponse.Error] в завсисимости от статуса запроса.
 * Ответ ApiResponse содержит статус HTTP, заголовки и обработанные данные ответа
 *
 * @throws SerializationException Если не правильно указан тип принимаемых данных
 * @throws ServerResponseException Если в ответе сервера содержится ошибка
 * @throws UnknownHostException Если нет подключения к Интернету
 * @throws IOException Если во время сетевого подключения возникают ошибки
 */
@Throws(
    SerializationException::class,
    ServerResponseException::class,
    UnknownHostException::class,
    IOException::class,
)
suspend inline fun <reified SuccessResponse, reified ErrorResponse> HttpClient.createRequest(
    context: Context,
    path: String,
    method: HttpMethod,
    protocol: URLProtocol = URLProtocol.byName[BuildConfig.PROTOCOL]!!,
    host: String = BuildConfig.HOST,
    port: Int = BuildConfig.PORT,
    params: StringValues = StringValues.Empty,
    body: @Serializable Any? = null,
    overrideToken: String? = null,
): ApiResponse<out SuccessResponse, out ErrorResponse> = try {
    val response =
        request {
            this.method = method
            url {
                this.protocol = protocol
                this.host = host
                this.port = port
                path(path)
                parameters.appendAll(params)
            }
            contentType(ContentType.Application.Json)
            setHeaders(overrideToken, context)
            body(body)
        }

    val responseBody = response.body<String>()

    if (response.status.isSuccess()) {
        ApiResponse.Success(
            status = response.status,
            headers = response.headers,
            body = json.decodeFromString<SuccessResponse>(responseBody),
        )
    } else {
        ApiResponse.Error(
            status = response.status,
            headers = response.headers,
            body = json.decodeFromString<ErrorResponse>(responseBody),
        )
    }
} catch (e: SerializationException) {
    ErrorResponse::class.qualifiedName?.let { Log.e("SerializationException", it) }

    ApiResponse.Error(
        status = HttpStatusCode.SerializationException,
    )
} catch (e: ServerResponseException) {
    ErrorResponse::class.qualifiedName?.let { Log.e("ServerResponseException", it) }

    ApiResponse.Error(
        status = HttpStatusCode.InternalServerError,
    )
} catch (e: UnknownHostException) { // Нет интернета
    ErrorResponse::class.qualifiedName?.let { Log.e("UnknownHostException", it) }

    ApiResponse.Error(
        status = HttpStatusCode.UnknownHostException,
    )
} catch (e: IOException) { // Ошибки соединений
    ErrorResponse::class.qualifiedName?.let { Log.e("IOException", it) }

    ApiResponse.Error(
        status = HttpStatusCode.IOException,
    )
}

suspend fun HttpRequestBuilder.setHeaders(
    overrideToken: String?,
    context: Context,
) {
    overrideToken?.let {
        header("Authorization", overrideToken)
    } ?: run {
        header("Authorization", Store(Keys.TOKEN, context).get())
    }
}

fun HttpRequestBuilder.body(body: Any?) {
    if(body == null) return

    setBody(body)
}
