package com.novapay.ui.models.transactions

import java.io.Serializable
import java.math.BigDecimal


data class TransactionActivityOptions (val customerRef: String, val amount: BigDecimal): Serializable
