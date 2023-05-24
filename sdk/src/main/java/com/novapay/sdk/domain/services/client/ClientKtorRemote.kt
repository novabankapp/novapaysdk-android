package com.novapay.sdk.domain.services.client

import com.novapay.sdk.domain.models.responses.GeneralResult

class ClientKtorRemote constructor(
    private val clientService: ClientKtorService,
) : ClientRemote {
    override suspend fun validateApiKey(apiKey: String): GeneralResult = clientService.validateApiKey(apiKey)
}