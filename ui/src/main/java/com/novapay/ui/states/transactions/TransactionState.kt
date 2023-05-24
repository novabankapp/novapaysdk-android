package com.novapay.ui.states.transactions

import com.novapay.sdk.data.ValidatedCustomer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.math.BigDecimal

class TransactionState(
    val isLoading: Boolean = false,
    val errorMessage : String? = "",
    var customerRef :  String = "",
    var amount: BigDecimal = BigDecimal.ZERO,
    var metadata: Any? = null,
    var isGenerateTRNContentValid: Boolean = false,
    var trn: String? = null,
    var validatedCustomer: ValidatedCustomer? = null

) {
    companion object {
        fun initialise(): TransactionState = TransactionState()
    }
    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()
    class Builder(private val state: TransactionState) {
        var loading = state.isLoading
        var errorMessage = state.errorMessage
        var customerRef = state.customerRef
        var amount = state.amount
        var metadata = state.metadata
        var isGenerateTRNContentValid = state.isGenerateTRNContentValid
        var trn = state.trn
        var validatedCustomer = state.validatedCustomer


        fun build(): TransactionState {
            return TransactionState(
                loading,
                errorMessage,
                customerRef,
                amount,
                metadata,
                isGenerateTRNContentValid,
                trn,
                validatedCustomer

            )
        }


    }
}