package com.novapay.sdk.domain.services.merchants

import com.novapay.sdk.data.Merchant

interface MerchantRemote {
    suspend fun getMerchantDetails(token: String): Merchant?
}