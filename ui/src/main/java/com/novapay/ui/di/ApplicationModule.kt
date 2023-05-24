package com.novapay.ui.di

import com.novapay.sdk.platform.NovaPayPlatform
import com.novapay.sdk.platform.NovaPayPlatformProtocol
import com.novapay.ui.core.platform.uiSdk
import com.novapay.ui.core.platform.uiSdkProtocol
import org.koin.dsl.module

internal val applicationModule = module {
    single<uiSdkProtocol> { uiSdk }
    single<NovaPayPlatformProtocol> { NovaPayPlatform }
}