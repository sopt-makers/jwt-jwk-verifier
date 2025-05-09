package sopt.makers.jwt.verifier.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sopt.makers.jwt.verifier.jwt.exception.JwtException;
import sopt.makers.jwt.verifier.jwt.service.JwtAuthenticationService;
import sopt.makers.jwt.verifier.security.authentication.MakersAuthentication;

import java.io.IOException;

import static sopt.makers.jwt.verifier.jwt.code.JwtFailure.JWT_MISSING_AUTH_HEADER;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationService jwtAuthenticationService;
    private static final String ACCESS_TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationToken = getAuthorizationToken(request);
        MakersAuthentication authentication = jwtAuthenticationService.authenticate(authorizationToken);

        authentication.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private String getAuthorizationToken(final HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(ACCESS_TOKEN_PREFIX)) {
            throw new JwtException(JWT_MISSING_AUTH_HEADER);
        }
        return header.substring(ACCESS_TOKEN_PREFIX.length()).trim();
    }
}
