package com.novapay.novapaysdk

import android.app.Application
import com.novapay.sdk.platform.NovaPaySdkEnvironment
import com.novapay.ui.core.platform.novaPayUiSdk

class TestApp  : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeNovaSdk()
    }

    private fun initializeNovaSdk() {

        novaPayUiSdk.initializeWithApiKey(this,"yyyyy", NovaPaySdkEnvironment.valueOf("TST"))
    }
}