package com.novapay.ui.events.transactions

import com.novapay.sdk.domain.models.responses.GenerateTRNResponse
import java.math.BigDecimal

sealed class TransactionEvent {
    object Generate : TransactionEvent()
    object ValidateCustomerRef : TransactionEvent()
    data class ChangeCustomerRef(val customerRef : String): TransactionEvent()
    data class ChangeAmount( val amount: BigDecimal): TransactionEvent()
    data class ChangeMetadata(val metaData: Any): TransactionEvent()
}