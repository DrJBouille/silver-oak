package com.api.silver_oak_core.services;

import com.api.silver_oak_core.model.roles.RolesDTO;
import com.api.silver_oak_core.model.roles.RolesEntity;
import com.api.silver_oak_core.model.users.*;
import com.api.silver_oak_core.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {
  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;
  private final RolesService rolesService;

  public List<UserDTO> getUsers() {
    return usersRepository.findAll().stream().map(user ->
      UserDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .isEnabled(user.isEnabled())
        .roles(user.getRoles().stream().map(role -> RolesDTO.builder().roleName(role.getRoleName()).id(role.getId()).build()).collect(Collectors.toSet()))
        .build()).toList();
  }

  public Optional<UsersEntity> getUser(long id) {
    return usersRepository.findById(id);
  }

  public UsersEntity getUserByUsername(String username) {
    Optional<UsersEntity> user = usersRepository.findByUsernameAndIsEnabledTrue(username);
    if  (user.isEmpty()) throw new EntityNotFoundException("user " + username + " not found");
    return user.get();
  }

  public UserDTO getDTOByUsername(String username) {
    UsersEntity user = getUserByUsername(username);
    Set<RolesDTO> roles = user.getRoles().stream().map(role -> RolesDTO.builder().roleName(role.getRoleName()).id(role.getId()).build()).collect(Collectors.toSet());
    return UserDTO.builder().id(user.getId()).username(user.getUsername()).isEnabled(user.isEnabled()).roles(roles).build();
  }

  public UserDTO createUser(SigninRequest signinRequest) {
    Optional<RolesEntity> userRole = rolesService.getRole("USER");
    if (userRole.isEmpty()) throw new EntityNotFoundException("role not found");

    UsersEntity users = new UsersEntity();
    users.setUsername(signinRequest.getUsername());
    users.setPassword(passwordEncoder.encode(signinRequest.getPassword()));
    users.setRoles(Set.of(userRole.get()));

    UsersEntity savedUsers = usersRepository.save(users);

    return UserDTO.builder()
      .id(savedUsers.getId())
      .username(savedUsers.getUsername())
      .isEnabled(savedUsers.isEnabled())
      .roles(savedUsers.getRoles().stream().map(role -> RolesDTO.builder().roleName(role.getRoleName()).id(role.getId()).build()).collect(Collectors.toSet()))
      .build();
  }

  public UserDTO updateUsers(UsersUpdateRequestDTO dto) {
    Optional<UsersEntity> optionalUsers = getUser(dto.getId());
    if (optionalUsers.isEmpty()) throw new EntityNotFoundException("user " + dto.getId() + " not found");
    UsersEntity users = optionalUsers.get();

    users.setUsername(dto.getUsername());
    users.setRoles(dto.getRoles());

    UsersEntity savedUsers = usersRepository.save(users);

    return UserDTO.builder()
      .id(savedUsers.getId())
      .username(savedUsers.getUsername())
      .isEnabled(savedUsers.isEnabled())
      .roles(savedUsers.getRoles().stream().map(role -> RolesDTO.builder().roleName(role.getRoleName()).id(role.getId()).build()).collect(Collectors.toSet()))
      .build();
  }
}
