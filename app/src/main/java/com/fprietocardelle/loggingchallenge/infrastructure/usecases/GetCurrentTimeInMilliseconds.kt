package com.fprietocardelle.loggingchallenge.infrastructure.usecases

import javax.inject.Inject

class GetCurrentTimeInMilliseconds @Inject constructor() {
    operator fun invoke(): Long = System.currentTimeMillis()
}