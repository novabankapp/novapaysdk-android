package com.novapay.sdk.domain.models.responses

data class ValidateCustomerResponse(val customerName: String, val success: Boolean,val payload: Any)
