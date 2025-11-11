package com.api.silver_oak_core.model.users;

import com.api.silver_oak_core.model.BaseEntity;
import com.api.silver_oak_core.model.roles.RolesEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UsersEntity extends BaseEntity {

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(name = "is_enabled")
  private boolean isEnabled = true;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinTable(
    name = "user_role",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
  )
  private Set<RolesEntity> roles = new HashSet<>();
}
