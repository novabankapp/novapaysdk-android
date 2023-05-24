package com.novapay.sdk.domain.services.transactions

import com.novapay.sdk.domain.models.requests.GenerateTRNRequest
import com.novapay.sdk.domain.models.requests.ValidateCustomerRefRequest
import com.novapay.sdk.domain.models.responses.GenerateTRNResponse
import com.novapay.sdk.domain.models.responses.ValidateCustomerResponse

class TransactionsKtorRemote constructor(
    val transactionsKtorService: TransactionsKtorService
) : TransactionsRemote {
    override suspend fun validateCustomerRef(customerRef: ValidateCustomerRefRequest) = transactionsKtorService.validateCustomerRef(customerRef)

    override suspend fun generateTRN(request: GenerateTRNRequest): GenerateTRNResponse = transactionsKtorService.generateTRN(request)
}