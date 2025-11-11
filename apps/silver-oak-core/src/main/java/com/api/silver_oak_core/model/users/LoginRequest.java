package com.api.silver_oak_core.model.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
  @NotBlank private String username;
  @NotBlank private String password;
}
