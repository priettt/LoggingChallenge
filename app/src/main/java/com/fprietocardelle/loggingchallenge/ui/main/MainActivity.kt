package com.fprietocardelle.loggingchallenge.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fprietocardelle.loggingchallenge.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment())
                .commitNow()
        }
    }
}