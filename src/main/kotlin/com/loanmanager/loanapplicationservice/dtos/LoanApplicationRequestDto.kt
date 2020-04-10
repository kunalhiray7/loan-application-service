package com.loanmanager.loanapplicationservice.dtos

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class LoanApplicationRequestDto(

        @get: NotNull
        val customerId: Long,

        @get: NotNull
        @get: Min(value = 1, message = "Loan amount cannot be less than 1.")
        val amount: Double,

        @get: NotNull
        @get: Min(value = 1, message = "Loan duration cannot be less than 1.")
        val duration: Int
)
