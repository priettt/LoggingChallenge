package com.fprietocardelle.loggingchallenge.infrastructure.limiters

import com.fprietocardelle.loggingchallenge.infrastructure.usecases.GetCurrentTimeInMilliseconds
import javax.inject.Inject


/*
    This limiter allows submitting a single log every defined interval of time in milliseconds.
    For instance, if the interval is 500ms, it will only allow a single log every 500ms.
 */

class TimeLoggingLimiter @Inject constructor(
    private val timeLimitInMilliseconds: Long,
    private val getCurrentTime: GetCurrentTimeInMilliseconds
) : LoggingLimiter {

    private var lastTimeLogged = getCurrentTime()

    override fun canLog(message: String): Boolean {
        val currentTime = getCurrentTime()
        if (timeLimitHasPassed(currentTime)) {
            lastTimeLogged = currentTime
            return true
        }
        return false
    }

    private fun timeLimitHasPassed(currentTime: Long) = currentTime - lastTimeLogged >= timeLimitInMilliseconds
}