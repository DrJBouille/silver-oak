package com.api.silver_oak_core.model.users;

import com.api.silver_oak_core.model.BaseDTO;
import com.api.silver_oak_core.model.roles.RolesDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UserDTO extends BaseDTO {
  private Long id;

  private String username;

  private boolean isEnabled;

  private Set<RolesDTO> roles;
}
