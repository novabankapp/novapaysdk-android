package com.novapay.sdk.domain.services.transactions

import com.novapay.sdk.domain.models.requests.GenerateTRNRequest
import com.novapay.sdk.domain.models.requests.ValidateCustomerRefRequest
import com.novapay.sdk.domain.models.responses.GenerateTRNResponse
import com.novapay.sdk.domain.models.responses.ValidateCustomerResponse

interface TransactionsRemote {
    suspend fun validateCustomerRef(customerRef: ValidateCustomerRefRequest): ValidateCustomerResponse
    suspend fun generateTRN(request: GenerateTRNRequest): GenerateTRNResponse
}