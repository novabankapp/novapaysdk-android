package com.novapay.ui.core.platform

import android.app.Activity
import android.app.Application
import android.view.SurfaceControl.Transaction
import com.novapay.sdk.exceptions.Failure
import com.novapay.sdk.infrastructure.di.ExtraModule
import com.novapay.sdk.platform.NovaPayPlatform
import com.novapay.sdk.platform.SdkEnvironment
import androidx.lifecycle.ProcessLifecycleOwner
import com.novapay.ui.R
import com.novapay.ui.di.applicationModule
import com.novapay.ui.di.viewModelModule
import com.novapay.ui.features.transactions.TransactionActivity
import org.koin.core.module.Module
import java.lang.ref.WeakReference

interface uiSdkProtocol {

    fun initializeWithApiKey(
        application: Application,
        apiKey: String,
        environment: SdkEnvironment = SdkEnvironment.PRD,
        extraModules: List<ExtraModule> = listOf()
    )
    fun startGenerateTRNFlow(
        from: Activity,
        onSuccess: (() -> Unit)?,
        onError: ((Failure) -> Unit)?
    )
    fun initialize(application: Application, extraModules: List<ExtraModule> = listOf())

    fun setApiKey(apiKey: String, environment: SdkEnvironment)
}

object uiSdk : uiSdkProtocol{
    override fun initializeWithApiKey(
        application: Application,
        apiKey: String,
        environment: SdkEnvironment,
        extraModules: List<ExtraModule>
    ) {
        initialize(application, extraModules)
        setApiKey(apiKey, environment)
    }

    override fun startGenerateTRNFlow(
        from: Activity,
        onSuccess: (() -> Unit)?,
        onError: ((Failure) -> Unit)?
    ) {
        startFlow(from, onSuccess = onSuccess, onError = onError)
    }

    override fun initialize(application: Application, extraModules: List<ExtraModule>) {
        val list = getModuleList(extraModules)
        NovaPayPlatform.setUiModules(list)
        NovaPayPlatform.initialize(application)
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
    }


    override fun setApiKey(apiKey: String, environment: SdkEnvironment) {
        NovaPayPlatform.setApiKey(apiKey, environment)
    }
    private fun getModuleList(extraModules: List<ExtraModule>): MutableList<Module> {
        val list = mutableListOf(applicationModule,viewModelModule)
        val extra = extraModules.filter { it.module is Module }.map { it.module as Module }
        list.addAll(extra)
        return list
    }
    private fun startFlow(
        from: Activity,
        onSuccess: (() -> Unit)?,
        onError: ((Failure) -> Unit)?
    ) {
        from.startActivity(TransactionActivity.callingIntent(from))
        from.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        onSuccess?.invoke()


    }

}