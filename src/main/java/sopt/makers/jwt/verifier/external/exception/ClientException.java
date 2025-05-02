package sopt.makers.jwt.verifier.external.exception;

import sopt.makers.jwt.verifier.external.code.ClientFailure;
import sopt.makers.jwt.verifier.exception.BaseException;

public class ClientException extends BaseException {
    public ClientException(ClientFailure failure) {
        super(failure);
    }
}
