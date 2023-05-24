package com.novapay.sdk.domain.repositories.merchants

import com.novapay.sdk.data.Merchant
import com.novapay.sdk.domain.services.merchants.MerchantRemote

class MerchantRepositoryImpl constructor(
    private val merchantRemote: MerchantRemote
) : MerchantRepository {
    override suspend fun getMerchantDetails(token: String) = merchantRemote.getMerchantDetails(token)
}