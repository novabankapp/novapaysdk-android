package com.novapay.sdk.domain.models.requests

import java.math.BigDecimal


data class GenerateTRNRequest(val customerReference: String, val amount: BigDecimal,
                              val serviceUniqueIdentifier: String,val metaData: Any?)
