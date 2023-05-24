package com.novapay.sdk.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Error (

    val StatusCode : String,
    val Message : String
)