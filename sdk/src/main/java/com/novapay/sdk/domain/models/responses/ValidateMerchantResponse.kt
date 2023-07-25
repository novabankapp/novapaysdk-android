package com.novapay.sdk.domain.models.responses

import com.novapay.sdk.data.Merchant
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ValidateMerchantResponse(
    val success: Boolean,
    val merchant: Merchant
)
