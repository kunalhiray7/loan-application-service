package com.loanmanager.loanapplicationservice.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

object ObjectMapperUtil {

    fun getObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()

        objectMapper.registerModule(KotlinModule())

        return objectMapper
    }

}
