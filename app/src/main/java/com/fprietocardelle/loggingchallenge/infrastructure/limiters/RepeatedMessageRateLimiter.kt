package com.fprietocardelle.loggingchallenge.infrastructure.limiters

import com.fprietocardelle.loggingchallenge.infrastructure.usecases.GetCurrentTimeInMilliseconds
import javax.inject.Inject

/*
    Avoids logging the same message in an specified time window in milliseconds.

    Example: if the time window is 50ms, once a message is logged, we can only log it again after 50ms
*/

class RepeatedMessageRateLimiter @Inject constructor(
    private val timeWindow: Long,
    private val getCurrentTimeInMilliseconds: GetCurrentTimeInMilliseconds
) : LoggingLimiter {

    private var messageMap: MutableMap<String, Long> = mutableMapOf()

    override fun canLog(message: String): Boolean {
        val currentTime = getCurrentTimeInMilliseconds()
        val lastLoggedTime = messageMap.put(message, currentTime) ?: return true
        return isWindowSurpassed(currentTime, lastLoggedTime)
    }

    private fun isWindowSurpassed(currentTime: Long, lastLoggedTime: Long) = currentTime - lastLoggedTime > timeWindow
}