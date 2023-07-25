package com.novapay.sdk.data

@kotlinx.serialization.Serializable
data class MerchantConfig(val validationConfig: ValidationConfig?)
@kotlinx.serialization.Serializable
data class ValidationConfig(val isLocal : Boolean)
