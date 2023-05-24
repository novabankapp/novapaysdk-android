package com.novapay.sdk.domain.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class GeneralResult(
    val success : Boolean,
    val message : String
)