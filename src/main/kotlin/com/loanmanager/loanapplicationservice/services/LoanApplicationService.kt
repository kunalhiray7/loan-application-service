package com.loanmanager.loanapplicationservice.services

import com.loanmanager.loanapplicationservice.dtos.*
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

        getCustomerOrThrowNotFound(customerId)

        val loanApplication = loanApplicationRepository.save(loanApplicationRequest.mapToDomain())

        logger.info("Loan application is saved successfully for customer: $customerId")
        return LoanApplicationResponse(id = loanApplication.id!!)
    }

    @Throws(CustomerNotFoundException::class)
    fun getForCustomer(customerId: Long): LoanResponse? {
        logger.info("Fetching loan applications for customer: $customerId")
        val customer = getCustomerOrThrowNotFound(customerId)
        val loanApplications = loanApplicationRepository.findByCustomerId(customerId)

        return if(loanApplications.isEmpty()) {
            null
        } else {
            LoanResponse(
                    customer = CustomerDto.of(customer),
                    loans = loanApplications.map { LoanDto.of(it) }
            )
        }
    }

    private fun getCustomerOrThrowNotFound(customerId: Long) = (customerService.getById(customerId)
            ?: throw CustomerNotFoundException("No customer found for given id $customerId"))

}
