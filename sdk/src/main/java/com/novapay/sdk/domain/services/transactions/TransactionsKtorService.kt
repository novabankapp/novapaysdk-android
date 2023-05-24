package com.novapay.sdk.domain.services.transactions

import com.novapay.sdk.domain.models.requests.GenerateTRNRequest
import com.novapay.sdk.domain.models.requests.ValidateCustomerRefRequest
import com.novapay.sdk.domain.models.responses.GeneralResult
import com.novapay.sdk.domain.models.responses.GenerateTRNResponse
import com.novapay.sdk.domain.models.responses.ValidateCustomerResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class TransactionsKtorService constructor(private val client: HttpClient) {
    suspend fun validateCustomerRef(customerRef: ValidateCustomerRefRequest): ValidateCustomerResponse {
        val res : HttpResponse = client.request("Transactions/validateRef") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(customerRef)
        }
        val status = res.status
        return res.body()
    }
    suspend fun generateTRN(request: GenerateTRNRequest): GenerateTRNResponse {
        val res : HttpResponse = client.request("Transactions/GenerateTRN") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(request)
        }
        val status = res.status
        return res.body()
    }
}