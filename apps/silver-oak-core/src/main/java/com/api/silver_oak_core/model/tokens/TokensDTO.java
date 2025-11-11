package com.api.silver_oak_core.model.tokens;

import com.api.silver_oak_core.model.users.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokensDTO {
  private String accessToken;
  private String refreshToken;
  private UserDTO user;
}
