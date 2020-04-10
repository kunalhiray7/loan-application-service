package com.loanmanager.loanapplicationservice.services

import com.loanmanager.loanapplicationservice.dtos.LoanApplicationRequestDto
import com.loanmanager.loanapplicationservice.dtos.LoanApplicationResponse
import com.loanmanager.loanapplicationservice.exceptions.CustomerNotFoundException
import org.springframework.stereotype.Service

@Service
class LoanApplicationService {

    @Throws(CustomerNotFoundException::class)
    fun create(loanApplicationRequest: LoanApplicationRequestDto): LoanApplicationResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
