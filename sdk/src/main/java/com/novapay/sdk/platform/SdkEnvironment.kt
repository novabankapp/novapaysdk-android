package com.novapay.sdk.platform

import java.util.*

enum class SdkEnvironment(val baseUrl: String) {

    TST("https://api.sbx.aptopayments.com"),
    PRD("https://api.aptopayments.com");

    companion object {
        fun fromString(str: String) = valueOf(str.uppercase(Locale.US))
    }
}