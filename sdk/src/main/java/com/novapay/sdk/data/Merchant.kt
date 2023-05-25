package com.novapay.sdk.data

import kotlinx.serialization.Serializable


@Serializable
data class Merchant(val name: String, val recordId: Int,
                    val uniqueIdentifier: String, val isActive: Boolean, val merchantCategory: MerchantCategory,
                    val merchantConfig: MerchantConfig)
