package com.cryptoemergency.cryptoemergency.repository.store.data

import kotlinx.serialization.Serializable

@Serializable
enum class CurrentTheme {
    DARK,
    LIGHT,
    NULL,
}
