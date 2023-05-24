package com.novapay.sdk.domain.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val token : String,
    val refreshToken : String
)