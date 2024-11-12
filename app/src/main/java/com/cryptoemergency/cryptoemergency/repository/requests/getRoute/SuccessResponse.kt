package com.cryptoemergency.cryptoemergency.repository.requests.getRoute

import kotlinx.serialization.Serializable

@Serializable
data class SuccessResponse(
    val code: String,
    val routes: List<Route>,
    val waypoints: List<Waypoint>
)

@Serializable
data class Route(
    val geometry: Geometry,
    val legs: List<Leg>,
    val weight_name: String,
    val weight: Double,
    val duration: Double,
    val distance: Double
)

@Serializable
data class Geometry(
    val coordinates: List<List<Double>>,
    val type: String
)

@Serializable
data class Leg(
    val steps: List<Step>,
    val summary: String,
    val weight: Double,
    val duration: Double,
    val distance: Double
)

@Serializable
data object Step

@Serializable
data class Waypoint(
    val hint: String,
    val distance: Double,
    val name: String,
    val location: List<Double>
)
