package sopt.makers.jwt.verifier.user.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import sopt.makers.jwt.verifier.code.base.FailureCode;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public enum UserSuccess implements sopt.makers.jwt.verifier.code.base.SuccessCode {
  GET_USER_INFO(HttpStatus.OK, "유저 정보 조회에 성공했습니다");

  private final HttpStatus status;
  private final String message;
}
