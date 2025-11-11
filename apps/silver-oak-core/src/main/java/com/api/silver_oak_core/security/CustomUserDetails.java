package com.api.silver_oak_core.security;

import com.api.silver_oak_core.model.users.UsersEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

  private final UsersEntity usersEntity;

  public CustomUserDetails(UsersEntity usersEntity) {
    this.usersEntity = usersEntity;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.usersEntity.getRoles();
  }

  @Override
  public String getPassword() {
    return this.usersEntity.getPassword();
  }

  @Override
  public String getUsername() {
    return this.usersEntity.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.usersEntity.isEnabled();
  }
}
