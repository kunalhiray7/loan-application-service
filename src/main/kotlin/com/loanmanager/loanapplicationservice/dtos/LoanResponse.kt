package com.loanmanager.loanapplicationservice.dtos

import com.loanmanager.loanapplicationservice.domain.LoanApplicationStatus

data class LoanResponse(

        val customer: CustomerDto,

        val loans: List<LoanDto>
)

data class CustomerDto(
        val id: Long,

        val firstName: String,

        val lastName: String
)

data class LoanDto(
        val id: Long,

        val amount: Double,

        val duration: Int,

        val status: LoanApplicationStatus
)
