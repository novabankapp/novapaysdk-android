package com.novapay.sdk.domain.remote.ktor.mock.clients

import com.google.gson.Gson
import com.novapay.sdk.domain.models.responses.GeneralResult

object ValidateMockResponse{
    operator fun invoke(): String {
        var obj = GeneralResult(
            success = true,
            message = "Client Validated"
        )
        return Gson().toJson(obj)
    }
}