package com.cryptoemergency.cryptoemergency.repository.requests.getToken

import kotlinx.serialization.Serializable

@Serializable
data class SuccessResponse(
    val message: String
)
