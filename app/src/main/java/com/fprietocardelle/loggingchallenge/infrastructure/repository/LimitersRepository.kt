package com.fprietocardelle.loggingchallenge.infrastructure.repository

import com.fprietocardelle.loggingchallenge.infrastructure.limiters.LoggingLimiter
import javax.inject.Inject

class LimitersRepository @Inject constructor() {

    private var limiters = mutableListOf<LoggingLimiter>()

    fun getLimiters() = limiters.toList()

    fun storeLimiters(limiters: List<LoggingLimiter>) {
        this.limiters = limiters.toMutableList()
    }
}
