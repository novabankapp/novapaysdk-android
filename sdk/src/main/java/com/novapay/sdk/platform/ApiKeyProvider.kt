package com.novapay.sdk.platform

import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object ApiKeyProvider {

    var apiKey = ""
        private set
    var environment = SdkEnvironment.PRD
        private set

    fun getEnvironmentUrl() = environment.baseUrl

    fun set(apiKey: String, environment: SdkEnvironment) {
        this.apiKey = apiKey
        this.environment = environment
    }

    fun isCurrentEnvironmentPrd() = environment == SdkEnvironment.PRD
}