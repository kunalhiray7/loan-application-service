package com.loanmanager.loanapplicationservice.controllers

import com.loanmanager.loanapplicationservice.dtos.LoanApplicationRequestDto
import com.loanmanager.loanapplicationservice.services.LoanApplicationService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class LoanApplicationsController(private val loanApplicationService: LoanApplicationService) {

    @PostMapping("/api/loanapplications")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody loanApplicationRequest: LoanApplicationRequestDto) = loanApplicationService.create(loanApplicationRequest)

}
