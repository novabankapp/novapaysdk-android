package com.novapay.sdk.domain.services.client

import com.novapay.sdk.domain.models.responses.GeneralResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class ClientKtorService constructor(private val client: HttpClient) {
    suspend fun validateApiKey(apiKey: String): GeneralResult {
        val res : HttpResponse = client.request("Clients/validate") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(apiKey)
        }
        val status = res.status
        return res.body()
    }
}