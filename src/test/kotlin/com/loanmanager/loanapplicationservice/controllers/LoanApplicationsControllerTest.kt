package com.loanmanager.loanapplicationservice.controllers

import com.loanmanager.loanapplicationservice.domain.LoanApplicationStatus
import com.loanmanager.loanapplicationservice.dtos.*
import com.loanmanager.loanapplicationservice.exceptions.CustomerNotFoundException
import com.loanmanager.loanapplicationservice.services.LoanApplicationService
import com.loanmanager.loanapplicationservice.utils.ObjectMapperUtil
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
class LoanApplicationsControllerTest {

    private val mapper = ObjectMapperUtil.getObjectMapper()

    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var loanApplicationService: LoanApplicationService

    @InjectMocks
    private lateinit var loanApplicationsController: LoanApplicationsController

    @BeforeEach
    internal fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loanApplicationsController).build()
    }

    @Test
    fun `POST should create a loan application for a customer`() {
        val loanApplicationRequest = LoanApplicationRequestDto(
                customerId = 11L,
                amount = 1000.00,
                duration = 12
        )
        val loanApplicationResponse = LoanApplicationResponse(id = 11L)
        doReturn(loanApplicationResponse).`when`(loanApplicationService).create(loanApplicationRequest)

        mockMvc.perform(MockMvcRequestBuilders.post("/api/loanapplications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loanApplicationRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(loanApplicationResponse)))

        verify(loanApplicationService, times(1)).create(loanApplicationRequest)
    }

    @Test
    fun `POST should return 404 when no customer found for given customer id in the request`() {
        val loanApplicationRequest = LoanApplicationRequestDto(
                customerId = 11L,
                amount = 1000.00,
                duration = 12
        )
        doThrow(CustomerNotFoundException("Customer not found")).`when`(loanApplicationService).create(loanApplicationRequest)

        mockMvc.perform(MockMvcRequestBuilders.post("/api/loanapplications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loanApplicationRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound)

        verify(loanApplicationService, times(1)).create(loanApplicationRequest)
    }

    @Test
    fun `POST should return 400 when loan request is invalid`() {
        val loanApplicationRequest = LoanApplicationRequestDto(
                customerId = 11L,
                amount = 0.0,
                duration = 12
        )
        doThrow(CustomerNotFoundException("Customer not found")).`when`(loanApplicationService).create(loanApplicationRequest)

        mockMvc.perform(MockMvcRequestBuilders.post("/api/loanapplications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loanApplicationRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)

        verifyNoMoreInteractions(loanApplicationService)
    }

    @Test
    fun `GET should return the loan applications for given customer`() {
        val customerId = 123L
        val customer = CustomerDto(id = customerId, firstName = "John", lastName = "Smith")
        val loan = LoanDto(id = 234L, amount = 1115.00, duration = 12, status = LoanApplicationStatus.APPLIED)
        val loanResponse = LoanResponse(customer = customer, loans = listOf(loan))
        doReturn(loanResponse).`when`(loanApplicationService).getForCustomer(customerId = customerId)

        mockMvc.perform(get("/api/loanapplications").param("customerId", customerId.toString()))
                .andExpect(status().isOk)
                .andExpect(content().string(mapper.writeValueAsString(loanResponse)))

        verify(loanApplicationService, times(1)).getForCustomer(customerId)
    }

    @Test
    fun `GET should return NOT_FOUND when no customer found for given customerId`() {
        val customerId = 123L
        doThrow(CustomerNotFoundException("No customer found")).`when`(loanApplicationService).getForCustomer(customerId = customerId)

        mockMvc.perform(get("/api/loanapplications").param("customerId", customerId.toString()))
                .andExpect(status().isNotFound)

        verify(loanApplicationService, times(1)).getForCustomer(customerId)
    }

    @Test
    fun `GET should return NO_CONTENT when no loan application found for given customer`() {
        val customerId = 123L
        doReturn(null).`when`(loanApplicationService).getForCustomer(customerId = customerId)

        mockMvc.perform(get("/api/loanapplications").param("customerId", customerId.toString()))
                .andExpect(status().isNoContent)

        verify(loanApplicationService, times(1)).getForCustomer(customerId)
    }
}
