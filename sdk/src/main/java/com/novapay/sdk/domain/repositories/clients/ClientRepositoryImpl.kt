package com.novapay.sdk.domain.repositories.clients


import com.novapay.sdk.domain.services.client.ClientRemote
import com.novapay.sdk.domain.models.responses.GeneralResult

class ClientRepositoryImpl constructor(
    private val clientService: ClientRemote,
) : ClientRepository {
    override suspend fun validateApiKey(apiKey: String): GeneralResult = clientService.validateApiKey(apiKey)
}