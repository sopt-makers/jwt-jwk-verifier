package sopt.makers.jwt.verifier.exception;

import sopt.makers.jwt.verifier.code.jwk.JwkFailure;

public class JwkException extends BaseException {
    public JwkException(JwkFailure failure) {
        super(failure);
    }
}
