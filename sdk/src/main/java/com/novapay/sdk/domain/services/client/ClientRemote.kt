package com.novapay.sdk.domain.services.client

import com.novapay.sdk.domain.models.responses.GeneralResult
import com.novapay.sdk.domain.models.responses.ValidateMerchantResponse

interface ClientRemote {
    suspend fun validateApiKey(apiKey: String): ValidateMerchantResponse
}