package com.loanmanager.loanapplicationservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.loanmanager.loanapplicationservice.clients.CustomerServiceClient
import com.loanmanager.loanapplicationservice.utils.ObjectMapperUtil.getObjectMapper
import feign.Feign
import feign.Logger
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.okhttp.OkHttpClient
import feign.slf4j.Slf4jLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@SpringBootApplication
class LoanApplicationServiceApplication {

	@Value("\${customer.service.url}")
	private lateinit var customerServiceUrl: String

	@Bean
	@Primary
	fun serializingObjectMapper(): ObjectMapper {
		return getObjectMapper()
	}

	@Bean
	fun usersServiceClient(): CustomerServiceClient {
		return Feign.builder()
				.client(OkHttpClient())
				.encoder(JacksonEncoder(getObjectMapper()))
				.decoder(JacksonDecoder(getObjectMapper()))
				.logger(Slf4jLogger(CustomerServiceClient::class.java))
				.logLevel(Logger.Level.FULL)
				.target(CustomerServiceClient::class.java, customerServiceUrl)
	}

}

fun main(args: Array<String>) {
	runApplication<LoanApplicationServiceApplication>(*args)
}
