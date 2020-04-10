package com.loanmanager.loanapplicationservice.controllers

import com.loanmanager.loanapplicationservice.dtos.LoanApplicationRequestDto
import com.loanmanager.loanapplicationservice.dtos.LoanResponse
import com.loanmanager.loanapplicationservice.services.LoanApplicationService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
class LoanApplicationsController(private val loanApplicationService: LoanApplicationService) {

    @PostMapping("/api/loanapplications")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody loanApplicationRequest: LoanApplicationRequestDto) = loanApplicationService.create(loanApplicationRequest)

    @GetMapping("/api/loanapplications")
    fun getForCustomer(@RequestParam(name = "customerId", required = true) customerId: Long, httpResponse: HttpServletResponse): LoanResponse? {
        val loanResponse = loanApplicationService.getForCustomer(customerId)
        if (loanResponse == null) {
            httpResponse.status = NO_CONTENT.value()
        }

        return loanResponse
    }
}
