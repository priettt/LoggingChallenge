package com.fprietocardelle.loggingchallenge.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fprietocardelle.loggingchallenge.R
import dagger.hilt.android.AndroidEntryPoint


/* Overview
Our Android SDK allows users to capture logs in a session. To ensure the session payload does not get too large, we want to limit the number
 of logs that are captured during a session. To achieve this we want to implement one or more limits on log capture rate and count.

Tasks
1. Describe the rate and count limiters that you would want to implement to limit log capture, explaining how the limiting
would be done and stating the benefit of each limiter.
2. Create a sample Android app with a “Submit log button” and a text field to enter a log message in. Implement one or more of the log
limiters you proposed to determine if the message would be captured or not. Display on the screen if it would be captured or not.
 For simplicity you can:
a. Adjust any time-based parameters in the limiters to suit this test app which has the logs submitted by a person rather than
 having them come from code
b. Assume that there is no meta data for the logs, which would normally accompany a log message */

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModels()


}