package com.cryptoemergency.cryptoemergency.repository.requests.getRoute

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    val code: String,
)