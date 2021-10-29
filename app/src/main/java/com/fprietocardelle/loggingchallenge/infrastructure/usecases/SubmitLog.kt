package com.fprietocardelle.loggingchallenge.infrastructure.usecases

import com.fprietocardelle.loggingchallenge.infrastructure.network.LoggingRepository
import com.fprietocardelle.loggingchallenge.infrastructure.tools.LoggingLimiters
import javax.inject.Inject

class SubmitLog @Inject constructor(
    private val loggingLimiters: LoggingLimiters,
    private val loggingRepository: LoggingRepository
) {
    operator fun invoke(message: String): Result<Unit> {
        return if (loggingLimiters.canLog(message)) {
            loggingRepository.submitLog()
            Result.success(Unit)
        } else {
            Result.failure(Error())
        }
    }
}