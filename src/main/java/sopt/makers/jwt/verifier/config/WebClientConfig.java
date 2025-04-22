package sopt.makers.jwt.verifier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import sopt.makers.jwt.verifier.config.property.AuthClientProperty;

@Configuration
public class WebClientConfig {
    public static final String HEADER_API_KEY = "X-Api-Key";
    public static final String HEADER_SERVICE_NAME = "X-Service-Name";

    @Bean
    public WebClient authWebClient(AuthClientProperty property) {
        return WebClient.builder()
                .baseUrl(property.url())
                .defaultHeader(HEADER_API_KEY, property.apiKey())
                .defaultHeader(HEADER_SERVICE_NAME, property.serviceName())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
