package com.api.silver_oak_core.security;

import com.api.silver_oak_core.services.TokensService;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  private final TokensService tokenService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String jwt;
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    jwt = authHeader.substring(7);
    DecodedJWT decodedJWT;
    String username;

    try {
      decodedJWT = tokenService.verifyToken(jwt);
      username = decodedJWT.getSubject();
    } catch (Exception e) {
      sendError(response, new Exception("Token is invalid"));
      return;
    }

    if (username == null) {
      filterChain.doFilter(request, response);
      return;
    }

    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authToken);
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    filterChain.doFilter(request, response);
  }

  private void sendError(HttpServletResponse httpServletResponse, Exception e) throws IOException {
    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    httpServletResponse.setContentType("application/json");
    PrintWriter out = httpServletResponse.getWriter();
    ObjectMapper mapper = new ObjectMapper();
    out.println(mapper.writeValueAsString(Map.of("error", e.getMessage(), "code", "-1")));
    out.flush();
  }
}
