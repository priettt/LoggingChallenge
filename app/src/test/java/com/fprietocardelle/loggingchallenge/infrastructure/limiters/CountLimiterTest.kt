package com.fprietocardelle.loggingchallenge.infrastructure.limiters

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CountLimiterTest {

    private val testMessage = "hola"

    private lateinit var countLimiter: CountLimiter

    @Test
    fun `log once with a limit of 2`() {
        givenACountLimiterWithALimitOfTwo()
        val result = whenLoggingOnce()
        shouldLogOnce(result)
    }

    @Test
    fun `log twice with a limit of 2`() {
        givenACountLimiterWithALimitOfTwo()
        val resultList = whenLoggingTwice()
        shouldLogTwice(resultList)
    }

    @Test
    fun `log thrice with a limit of 2`() {
        givenACountLimiterWithALimitOfTwo()
        val resultList = whenLoggingThrice()
        shouldNotLogTheThirdTime(resultList)
    }

    private fun givenACountLimiterWithALimitOfTwo() {
        countLimiter = CountLimiter(2)
    }

    private fun whenLoggingOnce() = countLimiter.canLog(testMessage)

    private fun whenLoggingTwice(): List<Boolean> {
        return listOf(
            countLimiter.canLog(testMessage),
            countLimiter.canLog(testMessage),
        )
    }

    private fun whenLoggingThrice(): List<Boolean> {
        return listOf(
            countLimiter.canLog(testMessage),
            countLimiter.canLog(testMessage),
            countLimiter.canLog(testMessage),
        )
    }

    private fun shouldLogOnce(result: Boolean) {
        assertTrue(result)
    }

    private fun shouldLogTwice(resultList: List<Boolean>) {
        assertTrue(resultList[0])
        assertTrue(resultList[1])
    }

    private fun shouldNotLogTheThirdTime(resultList: List<Boolean>) {
        assertTrue(resultList[0])
        assertTrue(resultList[1])
        assertFalse(resultList[2])
    }
}