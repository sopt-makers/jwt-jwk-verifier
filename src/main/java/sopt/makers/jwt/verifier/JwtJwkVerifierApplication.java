package sopt.makers.jwt.verifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import sopt.makers.jwt.verifier.external.auth.AuthClientProperty;

@SpringBootApplication
@EnableConfigurationProperties(AuthClientProperty.class)
public class JwtJwkVerifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtJwkVerifierApplication.class, args);
	}

}
