package sopt.makers.jwt.verifier.exception;

import sopt.makers.jwt.verifier.code.failure.ClientFailure;

public class ClientException extends BaseException {
    public ClientException(ClientFailure failure) {
        super(failure);
    }
}
