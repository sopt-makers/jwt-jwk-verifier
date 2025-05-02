package sopt.makers.jwt.verifier.jwt.code;

import static lombok.AccessLevel.*;

import lombok.*;
import org.springframework.http.*;
import sopt.makers.jwt.verifier.code.base.*;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public enum JwtFailure implements FailureCode {
  JWT_PARSE_FAILED(HttpStatus.BAD_REQUEST, "잘못된 형식의 JWT입니다."),
  JWT_INVALID_CLAIMS(HttpStatus.UNAUTHORIZED, "JWT의 클레임이 유효하지 않습니다."),
  JWT_VERIFICATION_FAILED(HttpStatus.UNAUTHORIZED, "JWT 검증에 실패했습니다.");

  private final HttpStatus status;
  private final String message;
}
