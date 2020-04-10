package com.loanmanager.loanapplicationservice.repositories

import com.loanmanager.loanapplicationservice.domain.LoanApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoanApplicationRepository: JpaRepository<LoanApplication, Long>
