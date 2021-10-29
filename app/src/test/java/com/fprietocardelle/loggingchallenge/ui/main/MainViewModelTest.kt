package com.fprietocardelle.loggingchallenge.ui.main

import com.fprietocardelle.loggingchallenge.MainCoroutineRule
import com.fprietocardelle.loggingchallenge.infrastructure.usecases.SubmitLog
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val mockSubmitLog = mockk<SubmitLog>(relaxed = true)

    private val testMessage = "Hey"

    private lateinit var viewModel: MainViewModel

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        viewModel = MainViewModel(mockSubmitLog)
    }

    @Test
    fun `on unsuccessful log`() {
        givenLogIsUnsuccessful()
        whenLogButtonIsPressed()
        shouldExposeUnsuccessfulEvent()
    }

    @Test
    fun `on successful log`() {
        givenLogIsSuccessful()
        whenLogButtonIsPressed()
        shouldExposeSuccessfulEvent()
    }

    private fun givenLogIsUnsuccessful() {
        every { mockSubmitLog(any()) } returns Result.failure(Error())
    }

    private fun givenLogIsSuccessful() {
        every { mockSubmitLog(any()) } returns Result.success(Unit)
    }

    private fun whenLogButtonIsPressed() {
        viewModel.onLogButtonPressed(testMessage)
    }

    private fun shouldExposeUnsuccessfulEvent() = runBlockingTest {
        val event = (viewModel.events.first() as? MainViewModel.Event.EventLogged)
        assertFalse(event?.success ?: true)
    }

    private fun shouldExposeSuccessfulEvent() = runBlockingTest {
        val event = (viewModel.events.first() as? MainViewModel.Event.EventLogged)
        assertTrue(event?.success ?: true)
    }


}