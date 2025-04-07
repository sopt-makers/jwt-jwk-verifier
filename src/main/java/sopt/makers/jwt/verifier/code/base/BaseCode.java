package sopt.makers.jwt.verifier.code.base;

import org.springframework.http.HttpStatus;

public interface BaseCode {
  HttpStatus getStatus();

  String getMessage();
}
