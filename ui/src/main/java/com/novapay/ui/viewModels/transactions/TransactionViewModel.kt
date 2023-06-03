package com.novapay.ui.viewModels.transactions

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.novapay.sdk.data.Merchant
import com.novapay.sdk.platform.ApiKeyProvider
import com.novapay.sdk.platform.NovaPayPlatformProtocol
import com.novapay.ui.core.platform.BaseViewModel
import com.novapay.ui.events.transactions.TransactionEvent
import com.novapay.ui.states.transactions.TransactionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import java.math.BigDecimal

 class TransactionViewModel (
    private val protocol : NovaPayPlatformProtocol,
    private val apiKeyProvider: ApiKeyProvider
): BaseViewModel(), KoinComponent {
    private var merchant : Merchant? = null
    init{
        viewModelScope.launch {
            val exists = protocol.validateApiKey(apiKeyProvider.apiKey)
            merchant = protocol.getMerchant(apiKeyProvider.apiKey)
        }
    }
    private val _uiState = MutableStateFlow(TransactionState())
    val uiState: StateFlow<TransactionState> = _uiState

    fun handleTransactionEvent(event: TransactionEvent) {
        _uiState.value = uiState.value.build {
            when (event) {
                is TransactionEvent.LoadOptions -> {
                    amount = event.options.amount
                    customerRef = event.options.customerRef
                }
                is TransactionEvent.ChangeAmount -> {
                    amount = event.amount
                }
                is TransactionEvent.ChangeCustomerRef -> {
                    customerRef = event.customerRef
                }
                is TransactionEvent.ChangeMetadata -> {
                    metadata = event.metaData
                }
                TransactionEvent.Generate -> {
                    loading = true
                    errorMessage = null
                    generateTRN{ err ->
                        errorMessage = err
                        loading = false
                    }
                }
                TransactionEvent.ValidateCustomerRef -> {
                    validateCustomerRef()
                }
                else -> {

                }
            }

            Log.d("valid", (customerRef.trim().isNotEmpty() && amount != BigDecimal.ZERO).toString())
            isGenerateTRNContentValid = customerRef.trim().isNotEmpty() && amount != BigDecimal.ZERO && amount != BigDecimal("0.00")

        }
    }
    private fun generateTRN(loadError : (error: String) -> Unit){

        Log.d("generate", "within ${_uiState.value.isLoading}")
        viewModelScope.launch {
            try {
                Log.d("generate in scope", "Withing ${_uiState.value.isLoading}")
                if(merchant != null) {
                    Log.d("generate", "Merchant ${merchant}")
                    val res = protocol.generateTRN(
                         customerReference = _uiState.value.customerRef,
                         serviceUniqueIdentifier = merchant!!.uniqueIdentifier,
                         amount = _uiState.value.amount,
                         metaData = _uiState.value.metadata
                    )
                    Log.d("generate", "TRN ${res}")

                    _uiState.value = uiState.value.build {
                        loading = false
                        trn = res
                        errorMessage = null
                    }
                    Log.d("generate", "within ${uiState.value.isLoading}")

                }
                else {
                    loadError("Merchant not loaded")
                }

            }
            catch(ex : Exception){

                _uiState.value = uiState.value.build {
                    loading = false
                    errorMessage = "Something went wrong"
                }
            }
        }
    }
    private fun validateCustomerRef(){
        _uiState.value = uiState.value.build {
            loading = true
            errorMessage = null
        }
        viewModelScope.launch {
            try {
                if(merchant != null) {
                    val res = protocol.validateCustomerRef(_uiState.value.customerRef, merchant!!.uniqueIdentifier)
                    _uiState.value = uiState.value.build {
                        loading = false
                        errorMessage = null
                        validatedCustomer = res
                    }

                }
                _uiState.value = uiState.value.build {
                    loading = false
                    errorMessage = "Merchant not loaded"
                }

            }
            catch(ex : Exception){
                _uiState.value = uiState.value.build {
                    loading = false
                    errorMessage = ex.message
                }
            }
        }
    }
}