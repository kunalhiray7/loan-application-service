package com.loanmanager.loanapplicationservice.utils

import com.loanmanager.loanapplicationservice.utils.ObjectMapperUtil.getObjectMapper
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ObjectMapperUtilTest {

    @Test
    fun `objectMapper should have Kotlin module`() {
        val objectMapper = getObjectMapper()

        val registeredModuleIds = objectMapper.registeredModuleIds

        assertTrue(registeredModuleIds.contains("com.fasterxml.jackson.module.kotlin.KotlinModule"))
    }
}
