package com.api.silver_oak_core.model.roles;

import com.api.silver_oak_core.model.BaseEntity;
import com.api.silver_oak_core.model.users.UsersEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RolesEntity extends BaseEntity implements GrantedAuthority {
  @Column(name = "role_name", nullable = false)
  private String roleName;

  @ManyToMany(
    fetch = FetchType.LAZY,
    cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    mappedBy = "roles"
  )
  @JsonIgnore
  private Set<UsersEntity> users = new HashSet<>();

  @Override
  public String getAuthority() {
    return roleName;
  }
}
