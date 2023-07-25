package com.novapay.sdk.domain.services.client

import com.novapay.sdk.domain.models.responses.GeneralResult
import com.novapay.sdk.domain.models.responses.ValidateMerchantResponse

class ClientKtorRemote constructor(
    private val clientService: ClientKtorService,
) : ClientRemote {
    override suspend fun validateApiKey(apiKey: String): ValidateMerchantResponse = clientService.validateApiKey(apiKey)
}