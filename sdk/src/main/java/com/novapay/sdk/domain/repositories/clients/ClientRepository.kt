package com.novapay.sdk.domain.repositories.clients

import com.novapay.sdk.domain.models.responses.GeneralResult
import com.novapay.sdk.domain.models.responses.ValidateMerchantResponse

interface ClientRepository {
    suspend fun validateApiKey(apiKey: String): ValidateMerchantResponse
}