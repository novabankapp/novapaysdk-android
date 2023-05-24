package com.novapay.ui.models.transactions

import java.math.BigDecimal

data class GenerateTRN(val customerReference: String, val amount: BigDecimal,val metaData: Any)
