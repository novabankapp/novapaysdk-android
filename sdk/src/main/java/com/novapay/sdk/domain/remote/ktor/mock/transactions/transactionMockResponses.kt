package com.novapay.sdk.domain.remote.ktor.mock.transactions

import android.util.Log
import com.google.gson.Gson
import com.novapay.sdk.domain.models.responses.GeneralResult
import com.novapay.sdk.domain.models.responses.GenerateTRNResponse
import com.novapay.sdk.domain.models.responses.ValidateCustomerResponse

object GenerateTRNMockResponse{
    operator fun invoke(): String {
        var obj = GenerateTRNResponse(
            result = true,
            trn = "1234"
        )
        Log.d("in mock response", "uhuh")
        return Gson().toJson(obj)
    }
}
object ValidateCustomerMockResponse{
    operator fun invoke(): String {
        var obj = ValidateCustomerResponse(
            customerName = "Lewis Msasa",
            success = true,
            payload = "12334444"
        )
        return Gson().toJson(obj)
    }
}