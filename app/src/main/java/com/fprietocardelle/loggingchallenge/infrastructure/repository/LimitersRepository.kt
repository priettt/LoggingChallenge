package com.fprietocardelle.loggingchallenge.infrastructure.repository

import com.fprietocardelle.loggingchallenge.infrastructure.limiters.CountLimiter
import com.fprietocardelle.loggingchallenge.infrastructure.limiters.MessageLimiter
import com.fprietocardelle.loggingchallenge.infrastructure.limiters.RepeatedMessageRateLimiter
import com.fprietocardelle.loggingchallenge.infrastructure.limiters.TimeLimiter
import com.fprietocardelle.loggingchallenge.infrastructure.usecases.GetCurrentTimeInMilliseconds
import javax.inject.Inject

class LimitersRepository @Inject constructor(
    getCurrentTimeInMilliseconds: GetCurrentTimeInMilliseconds
) {

    private var limiters = listOf(
        CountLimiter(20),
        MessageLimiter(),
        RepeatedMessageRateLimiter(5000L, getCurrentTimeInMilliseconds),
        TimeLimiter(20L, getCurrentTimeInMilliseconds)
    )

    fun getLimiters() = limiters

}
