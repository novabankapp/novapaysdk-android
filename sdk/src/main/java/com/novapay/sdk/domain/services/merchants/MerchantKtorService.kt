package com.novapay.sdk.domain.services.merchants

import com.novapay.sdk.data.Merchant
import com.novapay.sdk.domain.models.requests.ValidateCustomerRefRequest
import com.novapay.sdk.domain.models.responses.ValidateCustomerResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class MerchantKtorService constructor(private val client: HttpClient) {
    suspend fun getMerchantDetails(token: String): Merchant? {
        val res : HttpResponse = client.request("Merchants/getDetails") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(token)
        }
        val status = res.status
        return res.body()
    }
}