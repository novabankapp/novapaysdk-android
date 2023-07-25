package com.novapay.sdk.domain.remote.ktor.mock.merchants

import com.google.gson.Gson
import com.novapay.sdk.data.Merchant
import com.novapay.sdk.data.MerchantCategory
import com.novapay.sdk.data.MerchantConfig
import com.novapay.sdk.domain.models.responses.GeneralResult

object MerchantMockResponse {
    operator fun invoke(): String {
        var obj = Merchant(
            name = "",
            recordId = 1234,
            uniqueIdentifier = "1234",
            isActive = true,
            merchantCategory = MerchantCategory(
                recordId = 1,
                name = "Water"
            ),
            merchantConfig =  MerchantConfig(
                validationConfig = null
            )
        )
        return Gson().toJson(obj)
    }
}