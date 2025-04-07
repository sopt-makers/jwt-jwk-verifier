package sopt.makers.jwt.verifier.code.jwk;

import static lombok.AccessLevel.*;

import lombok.*;
import org.springframework.http.*;
import sopt.makers.jwt.verifier.code.base.*;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public enum JwkFailure implements FailureCode {
  JWK_KID_NOT_FOUND(HttpStatus.UNAUTHORIZED, "해당 kid에 대한 공개키를 찾을 수 없습니다."),
  JWK_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "JWK 형식이 잘못되어 공개키를 파싱할 수 없습니다."),
  JWK_FETCH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "JWK 서버로부터 키를 가져오지 못했습니다.");

  private final HttpStatus status;
  private final String message;
}
