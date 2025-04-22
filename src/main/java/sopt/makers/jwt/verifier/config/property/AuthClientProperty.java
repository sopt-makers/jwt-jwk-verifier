package sopt.makers.jwt.verifier.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external.auth")
public record AuthClientProperty(
        String url,
        String apiKey,
        String serviceName,
        Endpoints endpoints
) {
    public record Endpoints(String jwk) {}
}
