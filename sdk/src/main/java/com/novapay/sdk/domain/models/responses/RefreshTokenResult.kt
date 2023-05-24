package com.novapay.sdk.domain.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResult(
    val token : String,
    val refreshToken : String
)