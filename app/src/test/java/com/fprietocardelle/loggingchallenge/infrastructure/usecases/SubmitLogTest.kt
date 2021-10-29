package com.fprietocardelle.loggingchallenge.infrastructure.usecases

import com.fprietocardelle.loggingchallenge.infrastructure.repository.LoggingRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Test

class SubmitLogTest {
    private val mockIsLoggingAllowed = mockk<IsLoggingAllowed>(relaxed = true)
    private val mockLoggingRepository = mockk<LoggingRepository>(relaxed = true)

    private val testMessage = "Hello"

    private var submitLog = SubmitLog(mockIsLoggingAllowed, mockLoggingRepository)

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
        every { mockIsLoggingAllowed(any()) } returns false
    }

    private fun givenMessageCanBeLogged() {
        every { mockIsLoggingAllowed(any()) } returns true
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