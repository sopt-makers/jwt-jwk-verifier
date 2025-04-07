package sopt.makers.jwt.verifier.exception;

import sopt.makers.jwt.verifier.code.base.*;

public abstract class BaseException extends RuntimeException {

    private final FailureCode failure;

    public BaseException(final FailureCode failure) {
        super(failure.getMessage());
        this.failure = failure;
    }

    public FailureCode getError() {
        return this.failure;
    }
}

