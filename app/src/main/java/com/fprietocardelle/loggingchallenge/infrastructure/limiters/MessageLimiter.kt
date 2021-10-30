package com.fprietocardelle.loggingchallenge.infrastructure.limiters

/*
    Limits the logging depending on the message logged.
    This could be useful in scenarios in which we only want to log certain events.
    For this example, we are discarding empty logs, and logs that contain the word "debug".
 */

const val LIMITED_WORD = "debug"

class MessageLimiter : LoggingLimiter {
    override fun canLog(message: String): Boolean =
        message.isNotBlank() && !message.contains(LIMITED_WORD)
}