package com.loanmanager.loanapplicationservice.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
data class LoanApplication (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @get: NotNull
        val customerId: Long = -1L,

        @get: NotNull
        val amount: Double = -1.0,

        @get: NotNull
        val duration: Int = -1,

        @get: NotNull
        val status: LoanApplicationStatus = LoanApplicationStatus.CREATED
)
