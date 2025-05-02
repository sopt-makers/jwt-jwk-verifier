package sopt.makers.jwt.verifier.external.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import sopt.makers.jwt.verifier.external.code.ClientFailure;
import sopt.makers.jwt.verifier.external.exception.ClientException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthClient {

    private final WebClient authWebClient;
    private final AuthClientProperty authProperty;

    public String getJwk() {
        try {
            return authWebClient.get()
                    .uri(authProperty.endpoints().jwk())
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorMap(WebClientResponseException.class, ex -> {
                        log.error("Failed to receive response from Auth server: {}", ex.getResponseBodyAsString(), ex);
                        return new ClientException(ClientFailure.RESPONSE_ERROR);
                    })
                    .block();
        } catch (RuntimeException e) {
            log.error("Unexpected exception occurred during Auth server communication: {}", e.getMessage(), e);
            throw new ClientException(ClientFailure.COMMUNICATION_ERROR);
        }
    }
}