package com.loanmanager.loanapplicationservice.services

import com.loanmanager.loanapplicationservice.dtos.CustomerResponse
import com.loanmanager.loanapplicationservice.dtos.LoanApplicationRequestDto
import com.loanmanager.loanapplicationservice.dtos.LoanApplicationResponse
import com.loanmanager.loanapplicationservice.exceptions.CustomerNotFoundException
import com.loanmanager.loanapplicationservice.repositories.LoanApplicationRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class LoanApplicationServiceTest {

    @Mock
    private lateinit var customerService: CustomerService

    @Mock
    private lateinit var loanApplicationRepository: LoanApplicationRepository

    @InjectMocks
    private lateinit var loanApplicationService: LoanApplicationService

    @Test
    fun `create() should create a loan application for given customer`() {
        val loanApplicationRequest = LoanApplicationRequestDto(
                customerId = 11L,
                amount = 1000.00,
                duration = 12
        )
        val savedEntityId = 123L
        val loanApplication = loanApplicationRequest.mapToDomain()
        val customer = CustomerResponse(
                id = loanApplicationRequest.customerId,
                userId = 123L,
                firstName = "John",
                lastName = "Smith",
                email = "johnsmith@example.com",
                phone = "+49 1234567890"
        )
        doReturn(customer).`when`(customerService).getById(loanApplicationRequest.customerId)
        doReturn(loanApplication.copy(id = savedEntityId)).`when`(loanApplicationRepository).save(loanApplication)

        val result = loanApplicationService.create(loanApplicationRequest)

        assertEquals(LoanApplicationResponse(id = savedEntityId), result)
        verify(customerService, times(1)).getById(loanApplicationRequest.customerId)
        verify(loanApplicationRepository, times(1)).save(loanApplication)
    }

    @Test
    fun `create() should throw CustomerNotFoundException when no customer found for given customer id`() {
        val customerId = 11L
        val loanApplicationRequest = LoanApplicationRequestDto(
                customerId = customerId,
                amount = 1000.00,
                duration = 12
        )
        doReturn(null).`when`(customerService).getById(customerId)

        val result = assertThrows<CustomerNotFoundException> { loanApplicationService.create(loanApplicationRequest) }

        assertEquals("No customer found for given id $customerId", result.message)
        verify(customerService, times(1)).getById(customerId)
        verifyNoMoreInteractions(loanApplicationRepository)
    }
}
