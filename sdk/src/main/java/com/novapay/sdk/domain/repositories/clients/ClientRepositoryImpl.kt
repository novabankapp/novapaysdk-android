package com.novapay.sdk.domain.repositories.clients


import com.novapay.sdk.domain.services.client.ClientRemote
import com.novapay.sdk.domain.models.responses.GeneralResult
import com.novapay.sdk.domain.models.responses.ValidateMerchantResponse

class ClientRepositoryImpl constructor(
    private val clientService: ClientRemote,
) : ClientRepository {
    override suspend fun validateApiKey(apiKey: String): ValidateMerchantResponse = clientService.validateApiKey(apiKey)
}