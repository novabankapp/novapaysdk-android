package com.novapay.sdk.platform

import java.util.*

enum class NovaPaySdkEnvironment(val baseUrl: String) {

    TST("https://api.test.novapayments.com"),
    PRD("https://api.novapayments.com");

    companion object {
        fun fromString(str: String) = valueOf(str.uppercase(Locale.US))
    }
}