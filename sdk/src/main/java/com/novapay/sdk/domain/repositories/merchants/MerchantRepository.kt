package com.novapay.sdk.domain.repositories.merchants

import com.novapay.sdk.data.Merchant

interface MerchantRepository {

    suspend fun getMerchantDetails(token: String): Merchant?
}