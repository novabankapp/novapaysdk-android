package com.novapay.sdk.domain.services.client

import com.novapay.sdk.domain.models.responses.GeneralResult

interface ClientRemote {
    suspend fun validateApiKey(apiKey: String): GeneralResult
}