package com.novapay.sdk.domain.remote.ktor

import android.util.Log
import com.google.gson.Gson
import com.novapay.sdk.BuildConfig
import com.novapay.sdk.domain.exceptions.ExceptionHandler
import com.novapay.sdk.domain.exceptions.ServerException
import com.novapay.sdk.domain.interceptors.AuthInterceptor
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class KtorClient {

    companion object {

        private const val TIME_OUT = 600_000
        fun getClient(authInteceptor : AuthInterceptor) : HttpClient {


            return  HttpClient(OkHttp) {
                engine {
                    config {
                        addInterceptor(authInteceptor)
                    }
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = TIME_OUT.toLong()
                }

                defaultRequest {
                    host = "${BuildConfig.NovaUrl}"
                    url {

                        protocol = URLProtocol.HTTPS
                    }
                }
                var responseBody = "";
                //expectSuccess = true

                HttpResponseValidator {

                    validateResponse { response ->
                        val statusCode = response.status
                        Log.e("status=>", statusCode.value.toString())

                        when (statusCode.value) {
                            in 300..399 -> {
                                responseBody = response.bodyAsText()
                                if (responseBody.isNotEmpty() && responseBody.contains(
                                        "Message",
                                        true
                                    )
                                ) {
                                    val gson = Gson()
                                    val error = gson.fromJson(
                                        responseBody,
                                        com.novapay.sdk.domain.models.Error::class.java
                                    )
                                    Log.e("error ${statusCode.value}", " ${error.Message}")
                                    throw ServerException(error.Message)
                                }
                                throw Exception("Something went wrong")
                            }
                            in 400..499 -> {
                                responseBody = response.bodyAsText()
                                if (responseBody.isNotEmpty() && responseBody.contains(
                                        "Message",
                                        true
                                    )
                                ) {
                                    val gson = Gson()
                                    val error = gson.fromJson(
                                        responseBody,
                                        com.novapay.sdk.domain.models.Error::class.java
                                    )
                                    Log.e("error ${statusCode.value}", " ${error.Message}")
                                    throw ServerException(error.Message)
                                }
                                throw Exception("Something went wrong")
                            }
                            in 500..599 -> {
                                if (responseBody.isNotEmpty() && responseBody.contains(
                                        "Message",
                                        true
                                    )
                                ) {
                                    responseBody = response.bodyAsText()
                                    val gson = Gson()
                                    val error = gson.fromJson(
                                        responseBody,
                                        com.novapay.sdk.domain.models.Error::class.java
                                    )
                                    Log.e("error ${statusCode.value}", " ${error.Message}")
                                    throw ServerException(error.Message)
                                }
                            }

                        }
                        if (statusCode.value >= 600) {
                            responseBody = response.bodyAsText()
                            throw ServerException(responseBody)
                        }
                    }

                    handleResponseExceptionWithRequest { exception, httpRequest ->
                        val clientException = exception as? ClientRequestException
                            ?: return@handleResponseExceptionWithRequest
                        val response = clientException.response
                        val statusCode = response.status

                        Log.e("error2", "${statusCode.value}")
                        val error = when (httpRequest) {

                            is HttpRequest -> ExceptionHandler.getError(response)
                            else -> throw Exception("Something went wrong ${responseBody}") //or do whatever you need
                        }
                        Log.e("error2", "${error.errorDescription}")
                        throw ServerException(error.errorDescription)

                    }
                }



                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
                }

                install(Auth) {
                    basic {
                        sendWithoutRequest { request ->
                            request.url.host == "0.0.0.0"
                        }
                        credentials {
                            BasicAuthCredentials(
                                username = "",
                                password = ""
                            )
                        }
                    }
                }




                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.v("Logger Ktor =>", message)
                        }

                    }
                    level = LogLevel.ALL
                }

                install(ResponseObserver) {
                    onResponse { response ->
                        Log.d("HTTP status:", "${response.status.value}")
                        // Log.d("HTTP body:", "${response.bodyAsText()}")
                    }
                }

                install(DefaultRequest) {
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                    //header("","")
                }
            }
        }
    }
}