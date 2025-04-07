package sopt.makers.jwt.verifier.exception;

import sopt.makers.jwt.verifier.code.base.*;

public class JwkException extends BaseException {
    public JwkException(FailureCode failure) {
        super(failure);
    }
}
