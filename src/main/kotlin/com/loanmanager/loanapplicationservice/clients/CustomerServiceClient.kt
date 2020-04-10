package com.loanmanager.loanapplicationservice.clients

import com.loanmanager.loanapplicationservice.dtos.CustomerResponse
import feign.Headers
import feign.RequestLine
import java.net.URI

interface CustomerServiceClient {

    @RequestLine("GET")
    @Headers("Content-Type: application/json")
    fun getUserById(uri: URI): CustomerResponse
}
