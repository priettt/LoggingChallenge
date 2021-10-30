package com.fprietocardelle.loggingchallenge.infrastructure.limiters

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MessageLimiterTest {
    private val messageLimiter = MessageLimiter()

    @Test
    fun `log empty message`() {
        val result = whenLoggingAnEmptyMessage()
        assertFalse(result)
    }

    @Test
    fun `log message with limited word`() {
        val result = whenLoggingMessageWithLimitedWord()
        assertFalse(result)
    }

    @Test
    fun `log another word`() {
        val result = whenLoggingAnotherWord()
        assertTrue(result)
    }

    private fun whenLoggingAnEmptyMessage() = messageLimiter.canLog("")

    private fun whenLoggingMessageWithLimitedWord() = messageLimiter.canLog("hey $LIMITED_WORD")

    private fun whenLoggingAnotherWord() = messageLimiter.canLog("another word")
}