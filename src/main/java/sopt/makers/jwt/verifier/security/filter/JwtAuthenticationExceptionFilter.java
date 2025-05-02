package sopt.makers.jwt.verifier.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sopt.makers.jwt.verifier.jwt.exception.JwtException;
import sopt.makers.jwt.verifier.common.util.ResponseUtil;

import java.io.IOException;

@Component
public class JwtAuthenticationExceptionFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      @NonNull final HttpServletRequest request,
      @NonNull final HttpServletResponse response,
      @NonNull final FilterChain filterChain)
      throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (JwtException e) {
      ResponseUtil.generateErrorResponse(response, e);
    }
  }
}
