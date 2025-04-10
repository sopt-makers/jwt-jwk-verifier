package sopt.makers.jwt.verifier.exception;

import sopt.makers.jwt.verifier.code.failure.JwtFailure;

public class JwtException extends BaseException {
    public JwtException(JwtFailure failure) {
        super(failure);
    }
}
