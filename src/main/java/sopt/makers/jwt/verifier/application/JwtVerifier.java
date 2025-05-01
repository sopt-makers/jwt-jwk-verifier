package sopt.makers.jwt.verifier.application;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.text.ParseException;
import sopt.makers.jwt.verifier.code.failure.JwtFailure;
import sopt.makers.jwt.verifier.exception.JwtException;
import sopt.makers.jwt.verifier.exception.JwkException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtVerifier {

    private final JwkProvider jwkProvider;

    @Value("${jwt.jwk.issuer}")
    private String issuer;

    public JWTClaimsSet extractClaims(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            String kid = jwt.getHeader().getKeyID();
            PublicKey key = jwkProvider.getPublicKey(kid);
            return verifyWithRetry(jwt, kid, key);
        } catch (ParseException e) {
            log.warn("JWT parsing failed: {}", e.getMessage());
            throw new JwtException(JwtFailure.JWT_PARSE_FAILED);
        }
    }

    private JWTClaimsSet verifyWithRetry(SignedJWT jwt, String kid, PublicKey publicKey) throws ParseException {
        try {
            return verify(jwt, publicKey);
        } catch (JOSEException e) {
            log.warn("JWT verification failed. reason={}", e.getMessage());
            return retryVerification(jwt, kid);
        }
    }

    /**
     * JWT 서명 검증 실패 시 캐시된 공개키를 무효화하고,
     * 인증 서버에서 공개키를 다시 가져와 재검증을 시도하는 로직입니다.
     *
     * - JWK 캐시가 만료되었거나 잘못된 키가 저장되어 있는 경우를 대비하여,
     *   서명 검증이 실패할 경우 한 번의 재시도 기회를 제공합니다.
     * - 키 재조회 후에도 검증이 실패하면 JwtException을 던집니다.
     *
     * @param jwt 서명을 검증할 JWT
     * @param kid JWT 헤더에 포함된 키 ID
     * @return 검증된 JWTClaimsSet
     * @throws ParseException JWT에서 클레임을 파싱하지 못한 경우
     * @throws JwtException 키 재조회 후에도 검증에 실패한 경우
     */
    private JWTClaimsSet retryVerification(SignedJWT jwt, String kid) throws ParseException {
        jwkProvider.invalidateKey(kid);
        try {
            PublicKey refreshedKey = jwkProvider.getPublicKey(kid);
            return verify(jwt, refreshedKey);
        } catch (JwkException | JOSEException e) {
            log.error("Re-verification failed. reason={}", e.getMessage());
            throw new JwtException(JwtFailure.JWT_VERIFICATION_FAILED);
        }
    }

    private JWTClaimsSet verify(SignedJWT jwt, PublicKey publicKey) throws JOSEException, ParseException {
        JWTClaimsSet claims = jwt.getJWTClaimsSet();
        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
        boolean signatureValid = jwt.verify(verifier);
        boolean issuerValid = issuer.equals(claims.getIssuer());
        boolean notExpired = claims.getExpirationTime().after(new Date());

        if (!(signatureValid && issuerValid && notExpired)) {
            log.warn("Invalid JWT claims detected. signatureValid={}, issuerValid={}, notExpired={}",
                    signatureValid, issuerValid, notExpired);
            throw new JwtException(JwtFailure.JWT_INVALID_CLAIMS);
        }
        return claims;
    }
}

