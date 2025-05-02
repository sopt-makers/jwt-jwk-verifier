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
import sopt.makers.jwt.verifier.application.JwtAuthenticationService;
import sopt.makers.jwt.verifier.security.authentication.MakersAuthentication;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtAuthenticationService jwtAuthenticationService;
  private static final int ACCESS_TOKEN_PREFIX = 7;

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
    String authorizationHeaderValue =
        request.getHeader(HttpHeaders.AUTHORIZATION).substring(ACCESS_TOKEN_PREFIX);
    return authorizationHeaderValue.trim();
  }
}
