package com.api.silver_oak_core.model.tokens;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
  @NotBlank
  private String refreshToken;
}
