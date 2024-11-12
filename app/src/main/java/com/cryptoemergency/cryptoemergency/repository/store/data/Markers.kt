package com.cryptoemergency.cryptoemergency.repository.store.data

import kotlinx.serialization.Serializable

@Serializable
data class Markers(
    val markers: List<Mark>
)

@Serializable
data class Mark(
    val longitude: Double,
    val latitude: Double,
    val title: String,
)
