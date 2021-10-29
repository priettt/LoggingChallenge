package com.fprietocardelle.loggingchallenge.infrastructure.usecases

import javax.inject.Inject

class SubmitLog @Inject constructor() {
    operator fun invoke(message: String?): Result<Unit> {
        return Result.success(Unit)
    }
}