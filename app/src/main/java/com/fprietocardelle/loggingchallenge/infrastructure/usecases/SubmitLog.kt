package com.fprietocardelle.loggingchallenge.infrastructure.usecases

import com.fprietocardelle.loggingchallenge.infrastructure.repository.LoggingRepository
import javax.inject.Inject

class SubmitLog @Inject constructor(
    private val isLoggingAllowed: IsLoggingAllowed,
    private val loggingRepository: LoggingRepository
) {
    operator fun invoke(message: String): Result<Unit> {
        return if (isLoggingAllowed(message)) {
            loggingRepository.submitLog()
            Result.success(Unit)
        } else {
            Result.failure(Error())
        }
    }
}