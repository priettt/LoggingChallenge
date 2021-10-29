package com.fprietocardelle.loggingchallenge.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.fprietocardelle.loggingchallenge.R
import com.fprietocardelle.loggingchallenge.databinding.MainFragmentBinding
import com.fprietocardelle.loggingchallenge.ui.main.MainViewModel.Event
import com.fprietocardelle.loggingchallenge.ui.main.MainViewModel.Event.EventLogged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding(view)
        observeViewModel()
    }

    private fun setupBinding(view: View) {
        binding = MainFragmentBinding.bind(view)
        binding.mainScreenButton.setOnClickListener {
            viewModel.onLogButtonPressed(binding.mainScreenTextInputLayout.editText?.text.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.events
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach(::handleEvent)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is EventLogged -> onEventLogged(event.success)
        }
    }

    private fun onEventLogged(success: Boolean) {
        binding.mainScreenLogStatus.text = if (success) {
            getString(R.string.main_screen_successful_log)
        } else {
            getString(R.string.main_screen_unsuccessful_log)
        }
    }

}