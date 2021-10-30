package com.fprietocardelle.loggingchallenge.infrastructure.limiters

import com.fprietocardelle.loggingchallenge.infrastructure.usecases.GetCurrentTimeInMilliseconds
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TimeLimiterTest {
    private val testMessage = "test"
    private val creationTime = 100L
    private val testTimeLimit = 5000L
    private val pastLimitCreationTime = creationTime + testTimeLimit + 1
    private val beforeLimitCreationTime = creationTime + testTimeLimit - 1

    private val mockGetCurrentTimeInMilliseconds = mockk<GetCurrentTimeInMilliseconds>(relaxed = true)

    private lateinit var timeLimiter: TimeLimiter

    @Before
    fun setUp() {
        every { mockGetCurrentTimeInMilliseconds() } returns creationTime
        timeLimiter = TimeLimiter(testTimeLimit, mockGetCurrentTimeInMilliseconds)
    }

    @Test
    fun `allow log when time limit has passed`() {
        val result = whenCanLogCalledAfterLimit()
        assertTrue(result)
    }

    @Test
    fun `forbid log when time limit has not passed`() {
        val result = whenCanLogCalledBeforeLimit()
        assertFalse(result)
    }

    private fun whenCanLogCalledAfterLimit(): Boolean {
        every { mockGetCurrentTimeInMilliseconds() } returns pastLimitCreationTime
        return timeLimiter.canLog(testMessage)
    }

    private fun whenCanLogCalledBeforeLimit(): Boolean {
        every { mockGetCurrentTimeInMilliseconds() } returns beforeLimitCreationTime
        return timeLimiter.canLog(testMessage)
    }
}
