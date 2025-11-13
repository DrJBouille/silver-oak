package com.api.silver_oak_core.services;

import com.api.silver_oak_core.model.tokens.TokensDTO;
import com.api.silver_oak_core.model.tokens.TokensEntity;
import com.api.silver_oak_core.model.users.UserDTO;
import com.api.silver_oak_core.repositories.TokensRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokensService {
  private final TokensRepository tokensRepository;
  private final UsersService usersService;

  @Value("${jwt-variables.KEY}")
  private String jwtKey;
  @Value("${jwt-variables.ISSUER}")
  private String jwtIssuer;
  @Value("${jwt-variables.EXPIRES_ACCESS_TOKEN_MINUTE}")
  private long accessTokenExpiryDuration;
  @Value("${jwt-variables.EXPIRES_REFRESH_TOKEN_MINUTE}")
  private long refreshTokenExpiryDuration;

  public TokensDTO generateTokenPairs(String username) {
    UserDTO users = usersService.getDTOByUsername(username);
    return getTokensDTO(users);
  }

  private TokensDTO getTokensDTO(UserDTO user) {
    String accessToken = generateAccessToken(user.getUsername());
    String refreshToken = generateRefreshToken(user.getUsername());

    return TokensDTO.builder()
      .refreshToken(refreshToken)
      .accessToken(accessToken)
      .user(user)
      .build();
  }

  public TokensDTO generateTokenPairsViaRefreshToken(String refreshTokenValue) {
    Optional<TokensEntity> existingRefreshToken = tokensRepository.findTokenByTokenAndValidTrue(refreshTokenValue);
    if (existingRefreshToken.isEmpty()) throw new EntityNotFoundException("Refresh token not found");

    verifyRefreshToken(existingRefreshToken.get());
    tokensRepository.delete(existingRefreshToken.get());
    UserDTO user = usersService.getDTOByUsername(existingRefreshToken.get().getUsername());
    return getTokensDTO(user);
  }

  public String generateAccessToken(String username) {
    return JWT.create()
      .withSubject(username)
      .withExpiresAt(new Date(System.currentTimeMillis() + Duration.ofMinutes(accessTokenExpiryDuration).toMillis()))
      .withIssuer(jwtIssuer)
      .sign(Algorithm.HMAC256(jwtKey.getBytes()));
  }

  public DecodedJWT verifyToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(jwtKey.getBytes(StandardCharsets.UTF_8));
    JWTVerifier verifier = JWT.require(algorithm).build();
    try {
      return verifier.verify(token);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public String generateRefreshToken(String username) {
    Instant expirationTime = Instant.now().plus(Duration.ofMinutes(refreshTokenExpiryDuration));
    TokensEntity refreshToken = new TokensEntity();
    refreshToken.setUsername(username);
    refreshToken.setValid(true);
    refreshToken.setExpiryDate(expirationTime);
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken = tokensRepository.save(refreshToken);
    return refreshToken.getToken();
  }

  public void verifyRefreshToken(TokensEntity token) {
    if (token.getExpiryDate().isBefore(Instant.now())) throw new RuntimeException("Token has expired");
  }
}
