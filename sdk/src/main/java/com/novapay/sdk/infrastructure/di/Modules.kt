package com.novapay.sdk.infrastructure.di

import com.novapay.sdk.domain.services.client.ClientKtorRemote
import com.novapay.sdk.domain.services.client.ClientKtorService
import com.novapay.sdk.domain.services.client.ClientRemote
import com.novapay.sdk.domain.interceptors.AuthInterceptor
import com.novapay.sdk.domain.remote.ktor.KtorClient
import com.novapay.sdk.domain.remote.ktor.mock.KtorMockClient
import com.novapay.sdk.domain.repositories.clients.ClientRepository
import com.novapay.sdk.domain.repositories.clients.ClientRepositoryImpl
import com.novapay.sdk.domain.repositories.merchants.MerchantRepository
import com.novapay.sdk.domain.repositories.merchants.MerchantRepositoryImpl
import com.novapay.sdk.domain.repositories.transactions.TransactionsRepository
import com.novapay.sdk.domain.repositories.transactions.TransactionsRepositoryImpl
import com.novapay.sdk.domain.services.merchants.MerchantKtorRemote
import com.novapay.sdk.domain.services.merchants.MerchantKtorService
import com.novapay.sdk.domain.services.merchants.MerchantRemote
import com.novapay.sdk.domain.services.transactions.TransactionsKtorRemote
import com.novapay.sdk.domain.services.transactions.TransactionsKtorService
import com.novapay.sdk.domain.services.transactions.TransactionsRemote
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val applicationModule = module {
    single { Preferences(context = androidContext()) }
    single { AuthInterceptor(get()) }
    single { KtorClient.getClient(get())
        //KtorMockClient.ktorHttpClient
    }
    single{ ClientKtorService(get()) }
    single<ClientRemote>{ ClientKtorRemote(get()) }
    single{ TransactionsKtorService(get()) }
    single<TransactionsRemote>{ TransactionsKtorRemote(get()) }
    single{ MerchantKtorService(get()) }
    single<MerchantRemote>{ MerchantKtorRemote(get()) }
}
internal val repositoryModule = module {
   single<ClientRepository>{ ClientRepositoryImpl(get()) }
   single<TransactionsRepository>{ TransactionsRepositoryImpl(get()) }
    single<MerchantRepository>{ MerchantRepositoryImpl(get()) }
}