package com.novapay.sdk.domain.models.requests

import com.novapay.sdk.functional.AnySerializer
import com.novapay.sdk.functional.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class GenerateTRNRequest(val customerReference: String,
                              @Serializable(with = BigDecimalSerializer::class)
                              val amount: BigDecimal,
                              val serviceUniqueIdentifier: String,
                              @Serializable(with = AnySerializer::class)
                              val metaData: Any?)
