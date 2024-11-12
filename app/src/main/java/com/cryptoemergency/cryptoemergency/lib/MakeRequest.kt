package com.cryptoemergency.cryptoemergency.lib

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.cryptoemergency.cryptoemergency.api.http.ApiResponse
import com.cryptoemergency.cryptoemergency.navigation.Destination
import com.cryptoemergency.cryptoemergency.repository.requests.getToken.getTokenRequest
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Отправляет запрос API и обрабатывает ответ.
 *
 * @param context [Context] Необходим вложеной функции для запроса токена в локальном хранилище
 * @param isLoading [Boolean] Переменная для указания состояния загрузки. Следует также передать в
 * [CommonButton]
 * @param message Переменная изменяемых состояний для хранения сообщений
 * @param onSuccess Функция обратного вызова, которая будет выполнена при успешном ответе
 * @param onRequest Функция самого запроса, которая отправляет запрос API и возвращает ответ API
 * @param errors [Map] Список пользовательских сообщений для определенных статусов
 * Если статус не найден на этой карте, будет использовано сообщение по умолчанию.
 *
 * @param Success Тип успешного ответа.
 * @param Error Тип ответа с ошибкой.
 *
 * @sample Sample
 */
suspend fun <Success, Error> makeRequest(
    context: Context,
    isLoading: MutableStateFlow<Boolean>,
    message: MutableStateFlow<String?>,
    errors: Map<HttpStatusCode, String> = emptyMap(),
    onRequest: suspend () -> ApiResponse<out Success, out Error>,
    onSuccess: suspend (ApiResponse.Success<Success>) -> Unit,
) = withContext(Dispatchers.IO) {
    isLoading.value = true

    val res = onRequest()

    when(res) {
        is ApiResponse.Success -> {
            isLoading.value = false

            onSuccess(res as ApiResponse.Success<Success>)
        }
        is ApiResponse.Error -> {
            isLoading.value = false

            message.value = errors[res.status] ?: Http.getDefaultMessages(context, res.status)
        }
    }
}


@Composable
private fun Sample() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val isLoading = remember { MutableStateFlow(false) }
    val redirect = remember { MutableStateFlow<Redirect?>(null) }
    val message = remember { MutableStateFlow<String?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            makeRequest(
                context = context,
                isLoading = isLoading,
                message = message,
                errors = mapOf(
                    HttpStatusCode.Conflict to "Conflict message",
                    HttpStatusCode.BadRequest to "Bad request",
                    HttpStatusCode.NoContent to "No content",
                ),
                onRequest = {
                    getTokenRequest(context)
                }
            ) {
                message.value = "Успешная авторизация!"
                redirect.value = Redirect(Destination.Home.Map)
            }
        }
    }
}
