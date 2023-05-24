package com.novapay.sdk.domain.services.merchants

import com.novapay.sdk.data.Merchant


class MerchantKtorRemote constructor(
    val merchantKtorService: MerchantKtorService
) : MerchantRemote {
    override suspend fun getMerchantDetails(token: String) = merchantKtorService.getMerchantDetails(token)

}