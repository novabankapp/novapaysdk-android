package com.novapay.sdk.platform

import com.novapay.sdk.data.AccessToken
import com.novapay.sdk.data.Merchant
import com.novapay.sdk.data.ValidatedCustomer
import com.novapay.sdk.domain.models.responses.GenerateTRNResponse
import java.math.BigDecimal

interface NovaPayPlatformProtocol {

    suspend fun validateCustomerRef(customerRef: String, serviceUniqueIdentifier: String) : ValidatedCustomer
    suspend fun generateTRN(customerReference: String, serviceUniqueIdentifier: String, amount: BigDecimal,metaData: Any?) : GenerateTRNResponse?
    suspend fun getMerchant(accessToken: String) : Merchant?
    suspend fun validateApiKey(apiKey: String) : Boolean
}