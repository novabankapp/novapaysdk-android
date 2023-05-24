package com.novapay.sdk.data

data class Merchant(val name: String, val recordId: Int,
                    val uniqueIdentifier: String, val isActive: Boolean, val merchantCategory: MerchantCategory,
                    val merchantConfig: MerchantConfig)
