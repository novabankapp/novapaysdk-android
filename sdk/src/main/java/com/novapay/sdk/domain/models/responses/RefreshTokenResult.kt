package com.novapay.sdk.domain.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResult(
    val access_token: String,
    val expires_in: Short,
    val token_type: String,
    val scope: String,
)