package com.cryptoemergency.cryptoemergency.repository.requests.common

import kotlinx.serialization.Serializable

@Serializable
data class SuccessResponse(
    val message: String,
)