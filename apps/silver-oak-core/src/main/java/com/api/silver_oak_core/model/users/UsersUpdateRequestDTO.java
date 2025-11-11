package com.api.silver_oak_core.model.users;

import com.api.silver_oak_core.model.roles.RolesEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UsersUpdateRequestDTO {
  private long id;

  private String username;

  private Set<RolesEntity> roles;
}
