package com.novapay.ui.viewModels.transactions

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
import org.koin.core.KoinComponent
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
                    generateTRN()
                }
                TransactionEvent.ValidateCustomerRef -> {
                    validateCustomerRef()
                }
                else -> {

                }
            }
            _uiState.value = uiState.value.build {
                isGenerateTRNContentValid = customerRef.trim().isNotEmpty() && !amount.equals(BigDecimal.ZERO)
            }
        }
    }
    private fun generateTRN(){
        _uiState.value = uiState.value.build {
            loading = true
            errorMessage = null
        }
        viewModelScope.launch {
            try {
                if(merchant != null) {
                    val res = protocol.generateTRN(
                         customerReference = _uiState.value.customerRef,
                         serviceUniqueIdentifier = merchant!!.uniqueIdentifier,
                         amount = _uiState.value.amount,
                         metaData = _uiState.value.metadata
                    )
                    _uiState.value = uiState.value.build {
                        loading = false
                        errorMessage = null
                        trn = res
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