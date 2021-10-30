package com.fprietocardelle.loggingchallenge.infrastructure.limiters

import com.fprietocardelle.loggingchallenge.infrastructure.usecases.GetCurrentTimeInMilliseconds
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RepeatedMessageRateLimiterTest {
    private val creationTime = 100L
    private val testTimeWindow = 5000L
    private val pastTimeWindow = creationTime + testTimeWindow + 1

    private val mockGetCurrentTimeInMilliseconds = mockk<GetCurrentTimeInMilliseconds>(relaxed = true)

    private lateinit var repeatedMessageRateLimiter: RepeatedMessageRateLimiter

    @Before
    fun setUp() {
        every { mockGetCurrentTimeInMilliseconds() } returns creationTime
        repeatedMessageRateLimiter = RepeatedMessageRateLimiter(testTimeWindow, mockGetCurrentTimeInMilliseconds)
    }

    @Test
    fun `logging multiple different messages`() {
        val result = whenLoggingDifferentMessages()
        shouldAllowLogging(result)
    }

    @Test
    fun `logging the same message before window passed`() {
        val result = whenLoggingTheSameMessageAgainBeforeWindowPassed()
        shouldAllowFirstLoggingButNotSecond(result)
    }

    @Test
    fun `logging the same message after window passed`() {
        val result = whenLoggingTheSameMessageAgainAfterWindowPassed()
        shouldAllowLogging(result)
    }

    private fun whenLoggingDifferentMessages(): List<Boolean> {
        return listOf(
            repeatedMessageRateLimiter.canLog("a"),
            repeatedMessageRateLimiter.canLog("b"),
            repeatedMessageRateLimiter.canLog("c"),
        )
    }

    private fun whenLoggingTheSameMessageAgainBeforeWindowPassed(): List<Boolean> {
        return listOf(
            repeatedMessageRateLimiter.canLog("a"),
            repeatedMessageRateLimiter.canLog("a")
        )
    }

    private fun whenLoggingTheSameMessageAgainAfterWindowPassed(): List<Boolean> {
        val list = mutableListOf(
            repeatedMessageRateLimiter.canLog("a"),
            repeatedMessageRateLimiter.canLog("b"),
            repeatedMessageRateLimiter.canLog("c"),
        )
        every { mockGetCurrentTimeInMilliseconds() } returns pastTimeWindow
        list.add(repeatedMessageRateLimiter.canLog("a"))
        return list
    }

    private fun shouldAllowFirstLoggingButNotSecond(result: List<Boolean>) {
        assertTrue(result[0])
        assertFalse(result[1])
    }

    private fun shouldAllowLogging(result: List<Boolean>) {
        result.forEach {
            assertTrue(it)
        }
    }
}
