package sopt.makers.jwt.verifier.jwt.exception;

import sopt.makers.jwt.verifier.jwt.code.JwkFailure;
import sopt.makers.jwt.verifier.exception.BaseException;

public class JwkException extends BaseException {
    public JwkException(JwkFailure failure) {
        super(failure);
    }
}
