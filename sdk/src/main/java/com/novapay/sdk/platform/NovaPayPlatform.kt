package com.novapay.sdk.platform

import android.app.Application
import androidx.annotation.RestrictTo
import com.jakewharton.threetenabp.AndroidThreeTen
import com.novapay.sdk.data.Merchant
import com.novapay.sdk.data.ValidatedCustomer
import com.novapay.sdk.domain.models.requests.GenerateTRNRequest
import com.novapay.sdk.domain.models.requests.ValidateCustomerRefRequest
import com.novapay.sdk.domain.repositories.clients.ClientRepository
import com.novapay.sdk.domain.repositories.merchants.MerchantRepository
import com.novapay.sdk.domain.repositories.transactions.TransactionsRepository
import com.novapay.sdk.infrastructure.di.applicationModule
import com.novapay.sdk.infrastructure.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import java.math.BigDecimal

object NovaPayPlatform : NovaPayPlatformProtocol {

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    lateinit var application: Application
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    lateinit var koin: Koin
    private var uiModules: List<Module> = listOf()

    fun initialize(application: Application) {
        this.application = application
        initKoin(application)
        AndroidThreeTen.init(application)
    }
    suspend fun initializeWithApiKey(application: Application, apiKey: String, environment: NovaPaySdkEnvironment = NovaPaySdkEnvironment.PRD) {
        initialize(application)
        setApiKey(apiKey, environment)
    }
    fun setUiModules(list: List<Any>) {
        uiModules = list.filterIsInstance<Module>()
    }
    private fun initKoin(application: Application) {
        this.koin = startKoin {
            androidContext(application)
            modules(getModules())
        }.koin
    }
    private val clientRepository: ClientRepository by lazy { koin.get() }
    override suspend fun validateApiKey(apiKey: String) : Boolean {
        var response = clientRepository.validateApiKey(apiKey)
        return response.success
    }
    fun setApiKey(apiKey: String, environment: NovaPaySdkEnvironment = NovaPaySdkEnvironment.PRD) {
        ApiKeyProvider.set(apiKey, environment)
    }

    private fun getModules(): List<Module> {
        val list = mutableListOf(applicationModule, repositoryModule)
        list.addAll(uiModules)
        return list
    }

    private val transactionsRepository: TransactionsRepository by lazy { koin.get() }
    override suspend fun validateCustomerRef(
        customerRef: String,
        serviceUniqueIdentifier: String
    ): ValidatedCustomer {
        val response = transactionsRepository.validateCustomerRef(ValidateCustomerRefRequest(
            customerReference = customerRef,
            serviceUniqueIdentifier = serviceUniqueIdentifier
        ))
        return ValidatedCustomer(
            customerName = response.customerName,
            success = response.success,
            payload = response.payload
        )
    }

    override suspend fun generateTRN(
        customerReference: String,
        serviceUniqueIdentifier: String,
        amount: BigDecimal,
        metaData: Any?
    ): String? {
        val response = transactionsRepository.generateTRN(
            GenerateTRNRequest(
                customerReference = customerReference,
                amount = amount,
                metaData = metaData,
                serviceUniqueIdentifier = serviceUniqueIdentifier
            ))
        return response.trn
    }
    private val merchantRepository: MerchantRepository by lazy { koin.get() }
    override suspend fun getMerchant(accessToken: String): Merchant? {
        return merchantRepository.getMerchantDetails(accessToken)
    }
}