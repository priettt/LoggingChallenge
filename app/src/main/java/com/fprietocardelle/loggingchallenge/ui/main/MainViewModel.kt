package com.fprietocardelle.loggingchallenge.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fprietocardelle.loggingchallenge.infrastructure.usecases.SubmitLog
import com.fprietocardelle.loggingchallenge.ui.main.MainViewModel.Event.EventLogged
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val submitLog: SubmitLog
) : ViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    fun onLogButtonPressed(message: String) {
        sendEvent(EventLogged(submitLog(message).isSuccess))
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    sealed class Event {
        data class EventLogged(val success: Boolean) : Event()
    }
}