package com.novapay.sdk.domain.repositories.clients

import com.novapay.sdk.domain.models.responses.GeneralResult

interface ClientRepository {
    suspend fun validateApiKey(apiKey: String): GeneralResult
}