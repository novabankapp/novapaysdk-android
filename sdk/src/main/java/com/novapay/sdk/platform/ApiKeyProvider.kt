package com.novapay.sdk.platform

import androidx.annotation.RestrictTo
import com.novapay.sdk.data.Merchant

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object ApiKeyProvider {

    var apiKey = ""
        private set
    var merchant : Merchant? = null
        private set
    var environment = NovaPaySdkEnvironment.PRD
        private set

    fun getEnvironmentUrl() = environment.baseUrl

    fun setMerchant (merchant: Merchant){
        this.merchant = merchant
    }

    fun set(apiKey: String, environment: NovaPaySdkEnvironment) {
        this.apiKey = apiKey
        this.environment = environment
    }

    fun isCurrentEnvironmentPrd() = environment == NovaPaySdkEnvironment.PRD
}