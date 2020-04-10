package com.loanmanager.loanapplicationservice.dtos

import com.loanmanager.loanapplicationservice.domain.LoanApplication
import com.loanmanager.loanapplicationservice.domain.LoanApplicationStatus

data class LoanResponse(

        val customer: CustomerDto,

        val loans: List<LoanDto>
)

data class CustomerDto(
        val id: Long,

        val firstName: String,

        val lastName: String
) {
    companion object {
        fun of(customerResponse: CustomerResponse) = CustomerDto(
                id = customerResponse.id,
                firstName = customerResponse.firstName,
                lastName = customerResponse.lastName
        )
    }
}

data class LoanDto(
        val id: Long,

        val amount: Double,

        val duration: Int,

        val status: LoanApplicationStatus
) {
    companion object {
        fun of(loanApplication: LoanApplication) = LoanDto(
                id = loanApplication.id!!,
                amount = loanApplication.amount,
                duration = loanApplication.duration,
                status = loanApplication.status
        )
    }
}
