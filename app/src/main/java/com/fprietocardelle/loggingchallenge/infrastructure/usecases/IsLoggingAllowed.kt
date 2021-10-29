package com.fprietocardelle.loggingchallenge.infrastructure.usecases

import com.fprietocardelle.loggingchallenge.infrastructure.repository.LimitersRepository
import javax.inject.Inject

class IsLoggingAllowed @Inject constructor(
    private val limitersRepository: LimitersRepository,
) {
    operator fun invoke(message: String): Boolean {
        return limitersRepository
            .getLimiters()
            .all { it.canLog(message) }
    }
}