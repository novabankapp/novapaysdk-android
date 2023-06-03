package com.novapay.sdk.domain.models.responses


@kotlinx.serialization.Serializable
data class GenerateTRNResponse(val result: Boolean, val trn: String)
