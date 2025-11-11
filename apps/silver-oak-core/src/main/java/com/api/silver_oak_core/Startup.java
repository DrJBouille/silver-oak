package com.api.silver_oak_core;

import com.api.silver_oak_core.model.roles.RolesDTO;
import com.api.silver_oak_core.model.roles.RolesEntity;
import com.api.silver_oak_core.model.users.SigninRequest;
import com.api.silver_oak_core.model.users.UsersEntity;
import com.api.silver_oak_core.model.users.UsersUpdateRequestDTO;
import com.api.silver_oak_core.services.RolesService;
import com.api.silver_oak_core.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Startup implements CommandLineRunner {
  private final RolesService rolesService;
  private final UsersService usersService;


  @Override
  public void run(String... args) throws Exception {
    RolesDTO adminRole = RolesDTO.builder().build();
    adminRole.setRoleName("ADMIN");
    adminRole = rolesService.createRole(adminRole);

    RolesDTO userRole = RolesDTO.builder().build();
    userRole.setRoleName("USER");
    rolesService.createRole(userRole);

    SigninRequest admin = new SigninRequest();
    admin.setUsername("admin");
    admin.setPassword("admin");
    usersService.createUser(admin);

    Optional<RolesEntity> adminRoleEntity = rolesService.getRole(adminRole.getRoleName());
    Optional<RolesEntity> userRoleEntity = rolesService.getRole(userRole.getRoleName());

    if (adminRoleEntity.isPresent() && userRoleEntity.isPresent()) {
      UsersUpdateRequestDTO usersUpdateRequestDTO = new UsersUpdateRequestDTO();
      UsersEntity usersEntity = usersService.getUserByUsername("admin");
      usersUpdateRequestDTO.setUsername(usersEntity.getUsername());
      usersUpdateRequestDTO.setId(usersEntity.getId());
      usersUpdateRequestDTO.setRoles(Set.of(adminRoleEntity.get(), userRoleEntity.get()));
      usersService.updateUsers(usersUpdateRequestDTO);
    }
  }
}
