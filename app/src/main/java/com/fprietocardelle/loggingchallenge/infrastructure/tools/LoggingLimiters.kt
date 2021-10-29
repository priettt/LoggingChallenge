package com.fprietocardelle.loggingchallenge.infrastructure.tools

import javax.inject.Inject

class LoggingLimiters @Inject constructor() {
    fun canLog(message: String): Boolean {
        return true
    }
}
