package com.api.silver_oak_core.model.roles;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RolesDTO {
  private long id;

  private String roleName;
}
