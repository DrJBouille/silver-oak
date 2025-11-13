package com.api.silver_oak_core.services;

import com.api.silver_oak_core.model.tokens.RefreshTokenRequest;
import com.api.silver_oak_core.model.tokens.TokensDTO;
import com.api.silver_oak_core.model.users.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final TokensService tokensService;
  private final AuthenticationManager authenticationManager;

  public TokensDTO login(LoginRequest loginRequest) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
      return tokensService.generateTokenPairs(loginRequest.getUsername());
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }

  public TokensDTO refreshToken(RefreshTokenRequest refreshTokenRequest) {
    return tokensService.generateTokenPairsViaRefreshToken(refreshTokenRequest.getRefreshToken());
  }
}
