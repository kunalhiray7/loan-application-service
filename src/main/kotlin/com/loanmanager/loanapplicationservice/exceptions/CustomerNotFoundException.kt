package com.loanmanager.loanapplicationservice.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class CustomerNotFoundException(override val message: String?) : RuntimeException(message) {
    companion object {
        private const val serialVersionUID = -2453166221529927594L
    }
}
