package com.novapay.sdk.domain.exceptions

import com.google.gson.Gson
import com.novapay.sdk.domain.models.GeneralError
import io.ktor.client.statement.*

class ExceptionHandler {

    companion object {
        suspend fun getError(response: HttpResponse): GeneralError {

            response.bodyAsText()?.let {

                var gson = Gson()

                return gson.fromJson(it, GeneralError::class.java);
            }
            throw IllegalArgumentException("not a parsable error")
        }
    }
}