package com.novapay.sdk.domain.services.client

import com.novapay.sdk.domain.models.requests.ValidateMerchantRequest
import com.novapay.sdk.domain.models.responses.GeneralResult
import com.novapay.sdk.domain.models.responses.ValidateMerchantResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

class ClientKtorService constructor(private val client: HttpClient) {
    suspend fun validateApiKey(apiKey: String): ValidateMerchantResponse {
        val res : HttpResponse = client.request("services/requests/merchants/validateApiKey") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(
                ValidateMerchantRequest(apiKey)
            )
        }
        val status = res.status
        return res.body()
    }
}