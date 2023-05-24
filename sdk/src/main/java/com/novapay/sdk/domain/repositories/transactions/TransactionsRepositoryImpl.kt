package com.novapay.sdk.domain.repositories.transactions

import com.novapay.sdk.domain.models.requests.GenerateTRNRequest
import com.novapay.sdk.domain.models.requests.ValidateCustomerRefRequest
import com.novapay.sdk.domain.models.responses.ValidateCustomerResponse
import com.novapay.sdk.domain.services.transactions.TransactionsRemote


class TransactionsRepositoryImpl constructor(
    val transactionsRemote: TransactionsRemote
) : TransactionsRepository {
    override suspend fun validateCustomerRef(customerRef: ValidateCustomerRefRequest) = transactionsRemote.validateCustomerRef(customerRef)

    override suspend fun generateTRN(request: GenerateTRNRequest) = transactionsRemote.generateTRN(request)
}