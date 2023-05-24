package com.novapay.ui.di

import com.novapay.sdk.platform.ApiKeyProvider
import com.novapay.ui.viewModels.transactions.TransactionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TransactionViewModel(get(), ApiKeyProvider) }
}