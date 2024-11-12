package com.cryptoemergency.cryptoemergency.repository.requests.getRoute

import android.content.Context
import com.cryptoemergency.cryptoemergency.api.http.ApiResponse
import com.cryptoemergency.cryptoemergency.api.http.createRequest
import com.cryptoemergency.cryptoemergency.api.http.httpClient
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import org.osmdroid.util.GeoPoint

suspend fun getRouteRequest(
    context: Context,
    pointsRoute: List<GeoPoint>
): ApiResponse<out SuccessResponse, out ErrorResponse> {
    val url = "/route/v1/driving/${
        pointsRoute.joinToString(";") { "${it.longitude},${it.latitude}" }
    }?overview=full&geometries=geojson"

    return httpClient.createRequest<SuccessResponse, ErrorResponse>(
        path = url,
        port = 80,
        protocol = URLProtocol.HTTP,
        host = "router.project-osrm.org",
        method = HttpMethod.Post,
        context = context,
    )
}

