package com.fprietocardelle.loggingchallenge.infrastructure.usecases

import com.fprietocardelle.loggingchallenge.infrastructure.network.LoggingRepository
import com.fprietocardelle.loggingchallenge.infrastructure.tools.LoggingLimiters
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Test

class SubmitLogTest {
    private val mockLoggingLimiters = mockk<LoggingLimiters>(relaxed = true)
    private val mockLoggingRepository = mockk<LoggingRepository>(relaxed = true)

    private val testMessage = "Hello"

    private var submitLog = SubmitLog(mockLoggingLimiters, mockLoggingRepository)

    @Test
    fun `message can not be logged`() {
        givenMessageCanNotBeLogged()
        val result = whenInvokingSubmitLog()
        shouldReturnFailure(result)
    }

    @Test
    fun `message can be logged`() {
        givenMessageCanBeLogged()
        val result = whenInvokingSubmitLog()
        shouldReturnSuccess(result)
        shouldSubmitLog()
    }

    private fun givenMessageCanNotBeLogged() {
        every { mockLoggingLimiters.canLog(any()) } returns false
    }

    private fun givenMessageCanBeLogged() {
        every { mockLoggingLimiters.canLog(any()) } returns true
    }

    private fun whenInvokingSubmitLog(): Result<Unit> = submitLog(testMessage)

    private fun shouldReturnFailure(result: Result<Unit>) {
        assertTrue(result.isFailure)
    }

    private fun shouldReturnSuccess(result: Result<Unit>) {
        assertTrue(result.isSuccess)
    }

    private fun shouldSubmitLog() {
        verify { mockLoggingRepository.submitLog() }
    }

}