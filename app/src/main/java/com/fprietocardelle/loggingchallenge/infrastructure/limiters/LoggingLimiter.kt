package com.fprietocardelle.loggingchallenge.infrastructure.limiters

interface LoggingLimiter {
    fun canLog(message: String): Boolean
}