package com.novapay.ui.core.platform

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.novapay.sdk.exceptions.Failure
import com.novapay.sdk.infrastructure.di.ExtraModule
import com.novapay.sdk.platform.NovaPayPlatform
import com.novapay.sdk.platform.NovaPaySdkEnvironment
import androidx.lifecycle.ProcessLifecycleOwner
import com.novapay.ui.R
import com.novapay.ui.di.applicationModule
import com.novapay.ui.di.viewModelModule
import com.novapay.ui.features.transactions.TransactionActivity
import com.novapay.ui.models.transactions.TransactionActivityOptions
import org.koin.core.module.Module

interface NovaPayUiSdkProtocol {

    fun initializeWithApiKey(
        application: Application,
        apiKey: String,
        environment: NovaPaySdkEnvironment = NovaPaySdkEnvironment.PRD,
        extraModules: List<ExtraModule> = listOf()
    )
    fun startGenerateTRNFlow(
        from: Activity,
        resultCode: Int? = null,
        activityResultLauncher: ActivityResultLauncher<Intent>? = null,
        options : TransactionActivityOptions? = null,
        onSuccess: (() -> Unit)?,
        onError: ((Failure) -> Unit)?
    )
    fun initialize(application: Application,
                   extraModules: List<ExtraModule> = listOf())

    fun setApiKey(apiKey: String, environment: NovaPaySdkEnvironment)
}

object novaPayUiSdk : NovaPayUiSdkProtocol{

    var OPTIONS : String = "options"
    override fun initializeWithApiKey(
        application: Application,
        apiKey: String,
        environment: NovaPaySdkEnvironment,
        extraModules: List<ExtraModule>
    ) {
        initialize(application, extraModules)
        setApiKey(apiKey, environment)
    }

    override fun startGenerateTRNFlow(
        from: Activity,
        resultCode: Int?,
        activityResultLauncher: ActivityResultLauncher<Intent>?,
        options : TransactionActivityOptions?,
        onSuccess: (() -> Unit)?,
        onError: ((Failure) -> Unit)?
    ) {
        startFlow(from, onSuccess = onSuccess, onError = onError, options = options , resultCode =  resultCode, activityResultLauncher = activityResultLauncher)
    }

    override fun initialize(application: Application, extraModules: List<ExtraModule>) {
        val list = getModuleList(extraModules)
        NovaPayPlatform.setUiModules(list)
        NovaPayPlatform.initialize(application)
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
    }


    override fun setApiKey(apiKey: String, environment: NovaPaySdkEnvironment) {
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
        resultCode: Int?,
        activityResultLauncher: ActivityResultLauncher<Intent>?,
        options : TransactionActivityOptions?,
        onSuccess: (() -> Unit)?,
        onError: ((Failure) -> Unit)?
    ) {
        if(resultCode != null)
            from.startActivityForResult(TransactionActivity.callingIntent(from, options),resultCode )
        else if(activityResultLauncher != null) {
            val intent = TransactionActivity.callingIntent(from, options)
            activityResultLauncher.launch(intent)
        }
        else
            from.startActivity(TransactionActivity.callingIntent(from, options))
        from.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        onSuccess?.invoke()


    }

}