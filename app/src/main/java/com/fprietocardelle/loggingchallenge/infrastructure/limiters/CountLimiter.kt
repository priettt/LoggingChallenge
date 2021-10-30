package com.fprietocardelle.loggingchallenge.infrastructure.limiters

/*
    Log until the limit is surpassed.
    For simplicity, we suppose that once this is called, the message is effectively logged.
 */

class CountLimiter(private val countLimit: Int) : LoggingLimiter {

    private var messagesLogged = 0

    override fun canLog(message: String): Boolean =
        ++messagesLogged <= countLimit

}