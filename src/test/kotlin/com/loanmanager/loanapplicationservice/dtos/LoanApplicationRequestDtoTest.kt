package com.loanmanager.loanapplicationservice.dtos

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.validation.Validation
import javax.validation.Validator

class LoanApplicationRequestDtoTest {

    private lateinit var validator: Validator

    private val loanApplicationRequest = LoanApplicationRequestDto(
            customerId = 11L,
            amount = 1000.00,
            duration = 12
    )

    @BeforeEach
    internal fun setUp() {
        val validatorFactory = Validation.buildDefaultValidatorFactory()
        validator = validatorFactory.validator
    }

    @Test
    fun `should not return any validation error if request is valid`() {
        val validations = validator.validate(loanApplicationRequest)

        assertEquals(0, validations.size)
    }

    @Test
    fun `should have validation error if loan amount is less than 1`() {
        val request = loanApplicationRequest.copy(amount = 0.0)

        val validations = validator.validate(request)

        assertEquals(1, validations.size)
    }

    @Test
    fun `should have validation error if loan duration is less than 1`() {
        val request = loanApplicationRequest.copy(duration = 0)

        val validations = validator.validate(request)

        assertEquals(1, validations.size)
    }
}
