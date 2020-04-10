package com.loanmanager.loanapplicationservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.loanmanager.loanapplicationservice.utils.ObjectMapperUtil.getObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@SpringBootApplication
class LoanApplicationServiceApplication {

	@Bean
	@Primary
	fun serializingObjectMapper(): ObjectMapper {
		return getObjectMapper()
	}

}

fun main(args: Array<String>) {
	runApplication<LoanApplicationServiceApplication>(*args)
}
