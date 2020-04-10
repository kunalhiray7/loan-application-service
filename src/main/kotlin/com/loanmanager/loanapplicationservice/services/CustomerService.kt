package com.loanmanager.loanapplicationservice.services

import com.loanmanager.loanapplicationservice.clients.CustomerServiceClient
import com.loanmanager.loanapplicationservice.dtos.CustomerResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

@Service
class CustomerService(private val customerServiceClient: CustomerServiceClient,
                      @Value("\${customer.service.url}")
                      private val customerServiceUrl: String) {

    companion object {
        private val logger = LoggerFactory.getLogger(CustomerService::class.java)
    }

    fun getById(customerId: Long): CustomerResponse? {
        logger.info("Calling customer-service to fetch customer for id: $customerId")
        val uri = UriComponentsBuilder.newInstance().path("$customerServiceUrl/api/customers/$customerId").build().toUri()
        return try {
            customerServiceClient.getUserById(uri)
        } catch (e: Throwable) {
            logger.info("Not able to fetch customer with id $customerId::", e)
            null
        }
    }

}
