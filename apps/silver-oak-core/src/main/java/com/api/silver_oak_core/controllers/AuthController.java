package com.api.silver_oak_core.controllers;

import com.api.silver_oak_core.model.tokens.RefreshTokenRequest;
import com.api.silver_oak_core.model.tokens.TokensDTO;
import com.api.silver_oak_core.model.users.LoginRequest;
import com.api.silver_oak_core.model.users.SigninRequest;
import com.api.silver_oak_core.services.AuthService;
import com.api.silver_oak_core.services.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;
  private final UsersService usersService;

  @PostMapping("/signin")
  public ResponseEntity<TokensDTO> signin(@Valid @RequestBody SigninRequest signinRequest) {
    try {
      usersService.createUser(signinRequest);

      LoginRequest loginRequest = new LoginRequest();
      loginRequest.setUsername(signinRequest.getUsername());
      loginRequest.setPassword(signinRequest.getPassword());

      return ResponseEntity.ok(authService.login(loginRequest));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/login")
  public ResponseEntity<TokensDTO> login(@Valid @RequestBody LoginRequest loginRequest) {
    try {
      if (usersService.getUserByUsername(loginRequest.getUsername()) == null) return ResponseEntity.notFound().build();
      return ResponseEntity.ok(authService.login(loginRequest));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<TokensDTO> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
    return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
  }
}
