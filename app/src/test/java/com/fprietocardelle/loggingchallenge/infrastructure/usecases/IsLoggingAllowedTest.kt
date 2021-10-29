package com.fprietocardelle.loggingchallenge.infrastructure.usecases

import com.fprietocardelle.loggingchallenge.infrastructure.limiters.LoggingLimiter
import com.fprietocardelle.loggingchallenge.infrastructure.repository.LimitersRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class IsLoggingAllowedTest {
    private val mockLimitersRepository = mockk<LimitersRepository>(relaxed = true)
    private val mockAllowingLimiter = mockk<LoggingLimiter>(relaxed = true) {
        every { canLog(any()) } returns true
    }
    private val anotherMockAllowingLimiter = mockk<LoggingLimiter>(relaxed = true) {
        every { canLog(any()) } returns true
    }
    private val mockNonAllowingLimiter = mockk<LoggingLimiter>(relaxed = true) {
        every { canLog(any()) } returns false
    }

    private var isLoggingAllowed = IsLoggingAllowed(mockLimitersRepository)

    @Test
    fun `no limiters`() {
        givenThereAreNoLimiters()
        val result = whenInvokingIsLoggingAllowed()
        assertTrue(result)
    }

    @Test
    fun `all limiters allow to log`() {
        givenAllLimitersAllowToLog()
        val result = whenInvokingIsLoggingAllowed()
        assertTrue(result)
    }

    @Test
    fun `one limiter does not allow to log`() {
        givenOneLimiterDoesNotAllowToLog()
        val result = whenInvokingIsLoggingAllowed()
        assertFalse(result)
    }

    private fun givenThereAreNoLimiters() {
        every { mockLimitersRepository.getLimiters() } returns emptyList()
    }

    private fun givenAllLimitersAllowToLog() {
        every { mockLimitersRepository.getLimiters() } returns listOf(mockAllowingLimiter, anotherMockAllowingLimiter)
    }

    private fun givenOneLimiterDoesNotAllowToLog() {
        every { mockLimitersRepository.getLimiters() } returns listOf(
            mockAllowingLimiter,
            mockNonAllowingLimiter,
            anotherMockAllowingLimiter
        )
    }

    private fun whenInvokingIsLoggingAllowed() = isLoggingAllowed("hey")
}