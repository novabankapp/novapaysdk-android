package com.novapay.ui.di

import com.novapay.sdk.platform.NovaPayPlatform
import com.novapay.sdk.platform.NovaPayPlatformProtocol
import com.novapay.ui.core.platform.novaPayUiSdk
import com.novapay.ui.core.platform.NovaPayUiSdkProtocol
import org.koin.dsl.module

internal val applicationModule = module {
    single<NovaPayUiSdkProtocol> { novaPayUiSdk }
    single<NovaPayPlatformProtocol> { NovaPayPlatform }
}