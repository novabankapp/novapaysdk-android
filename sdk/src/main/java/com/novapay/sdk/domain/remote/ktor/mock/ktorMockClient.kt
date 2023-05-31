package com.novapay.sdk.domain.remote.ktor.mock

import android.util.Log
import com.novapay.sdk.domain.models.responses.ValidateCustomerResponse
import com.novapay.sdk.domain.remote.ktor.mock.clients.ValidateMockResponse
import com.novapay.sdk.domain.remote.ktor.mock.merchants.MerchantMockResponse
import com.novapay.sdk.domain.remote.ktor.mock.transactions.GenerateTRNMockResponse
import com.novapay.sdk.domain.remote.ktor.mock.transactions.ValidateCustomerMockResponse
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class KtorMockClient {
    companion object {
        private const val TIME_OUT = 60_000

        val ktorHttpClient = HttpClient(MockEngine) {
            engine {
                /*addHandler { request ->
                    error("Unhandled ${request.url.encodedPath}")
                }*/
                addHandler { request ->
                    when (request.url.encodedPath) {
                        "/Merchants/validate" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))

                            respond(ValidateMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/Transactions/validateRef" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))

                            respond(ValidateCustomerMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/Transactions/GenerateTRN" -> {
                            Log.d("generate on ktor mock", " a little Within")
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            Log.d("generate on ktor mock", "Within")
                            respond(GenerateTRNMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/Merchants/getDetails" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))

                            respond(MerchantMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        else ->{
                            error("Unhandled ${request.url.encodedPath}")
                        }
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}