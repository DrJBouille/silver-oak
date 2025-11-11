package com.api.silver_oak_core.model.users;

import com.api.silver_oak_core.model.roles.RolesEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UsersRequestDTO {
  private String username;

  private String password;

  private Set<RolesEntity> roles;
}
