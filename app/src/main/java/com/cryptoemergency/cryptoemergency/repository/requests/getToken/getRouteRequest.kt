package com.cryptoemergency.cryptoemergency.repository.requests.getToken

import android.content.Context
import com.cryptoemergency.cryptoemergency.api.http.createRequest
import com.cryptoemergency.cryptoemergency.api.http.httpClient
import com.cryptoemergency.cryptoemergency.repository.requests.common.ErrorResponse
import com.cryptoemergency.cryptoemergency.repository.requests.common.PATH
import com.cryptoemergency.cryptoemergency.repository.requests.getRoute.SuccessResponse
import io.ktor.http.HttpMethod

suspend fun getTokenRequest(context: Context) =
    httpClient.createRequest<SuccessResponse, ErrorResponse>(
        path = "$PATH/guest",
        method = HttpMethod.Post,
        context = context,
    )
