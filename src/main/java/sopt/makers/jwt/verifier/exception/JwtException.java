package sopt.makers.jwt.verifier.exception;

import sopt.makers.jwt.verifier.code.JwtFailure;

public class JwtException extends BaseException {
    public JwtException(JwtFailure failure) {
        super(failure);
    }
}
