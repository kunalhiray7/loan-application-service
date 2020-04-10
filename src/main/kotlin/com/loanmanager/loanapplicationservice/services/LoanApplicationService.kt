package com.loanmanager.loanapplicationservice.services

import com.loanmanager.loanapplicationservice.dtos.LoanApplicationRequestDto
import com.loanmanager.loanapplicationservice.dtos.LoanApplicationResponse
import com.loanmanager.loanapplicationservice.exceptions.CustomerNotFoundException
import com.loanmanager.loanapplicationservice.repositories.LoanApplicationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LoanApplicationService(private val loanApplicationRepository: LoanApplicationRepository,
                             private val customerService: CustomerService) {

    companion object {
        private val logger = LoggerFactory.getLogger(LoanApplicationService::class.java)
    }

    @Throws(CustomerNotFoundException::class)
    fun create(loanApplicationRequest: LoanApplicationRequestDto): LoanApplicationResponse {
        val customerId = loanApplicationRequest.customerId
        logger.info("Loan application is being processed for customer: $customerId")

        customerService.getById(customerId)
                ?: throw CustomerNotFoundException("No customer found for given id $customerId")

        val loanApplication = loanApplicationRepository.save(loanApplicationRequest.mapToDomain())

        logger.info("Loan application is saved successfully for customer: $customerId")
        return LoanApplicationResponse(id = loanApplication.id!!)
    }

}
