package com.loanmanager.loanapplicationservice.services

import com.loanmanager.loanapplicationservice.clients.CustomerServiceClient
import com.loanmanager.loanapplicationservice.dtos.CustomerResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.util.UriComponentsBuilder

@ExtendWith(MockitoExtension::class)
class CustomerServiceTest {

    private val customerServiceUrl = "http://customer-service.com"

    @Mock
    private lateinit var customerServiceClient: CustomerServiceClient

    private lateinit var customerService: CustomerService

    @BeforeEach
    internal fun setUp() {
        customerService = CustomerService(customerServiceClient, customerServiceUrl)
    }

    @Test
    fun `getById() should return the customer for given customerId`() {
        val customerId = 123L
        val user = CustomerResponse(
                id = customerId,
                userId = 345L,
                firstName = "John",
                lastName = "Smith",
                email = "johnsmith@example.com",
                phone = "+49 3388661166"
        )
        val uri = UriComponentsBuilder.newInstance().path("$customerServiceUrl/api/customers/$customerId").build().toUri()
        Mockito.doReturn(user).`when`(customerServiceClient).getUserById(uri)

        val result = customerService.getById(customerId)

        assertEquals(user, result)
        Mockito.verify(customerServiceClient, Mockito.times(1)).getUserById(uri)
    }

    @Test
    fun `getById() should return null if any error occurs while getting customer`() {
        val userId = 123L
        val uri = UriComponentsBuilder.newInstance().path("$customerServiceUrl/api/customers/$userId").build().toUri()
        Mockito.doThrow(RuntimeException("Something went wrong")).`when`(customerServiceClient).getUserById(uri)

        val result = customerService.getById(userId)

        assertEquals(null, result)
        Mockito.verify(customerServiceClient, Mockito.times(1)).getUserById(uri)
    }
}
