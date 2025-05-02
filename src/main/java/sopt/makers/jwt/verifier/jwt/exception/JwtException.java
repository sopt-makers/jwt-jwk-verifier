package sopt.makers.jwt.verifier.jwt.exception;

import sopt.makers.jwt.verifier.jwt.code.JwtFailure;
import sopt.makers.jwt.verifier.exception.BaseException;

public class JwtException extends BaseException {
    public JwtException(JwtFailure failure) {
        super(failure);
    }
}
