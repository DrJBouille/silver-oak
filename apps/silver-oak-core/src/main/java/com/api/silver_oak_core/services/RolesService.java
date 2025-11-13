package com.api.silver_oak_core.services;

import com.api.silver_oak_core.model.roles.RolesDTO;
import com.api.silver_oak_core.model.roles.RolesEntity;
import com.api.silver_oak_core.repositories.RolesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RolesService {
  private final RolesRepository rolesRepository;

  public List<RolesDTO> getRoles() {
    return rolesRepository.findAll().stream().map(role -> RolesDTO.builder().roleName(role.getRoleName()).id(role.getId()).build()).toList();
  }

  public Optional<RolesEntity> getRolesById(long id) {
    return rolesRepository.findById(id);
  }

  public Optional<RolesEntity> getRole(String roleName) {
    return rolesRepository.findRolesEntityByRoleName(roleName);
  }

  public RolesDTO createRole(RolesDTO dto) {
    RolesEntity rolesEntity = new RolesEntity();
    rolesEntity.setRoleName(dto.getRoleName());

    RolesEntity savedRole = rolesRepository.save(rolesEntity);
    return RolesDTO.builder().roleName(savedRole.getRoleName()).id(savedRole.getId()).build();
  }

  public RolesDTO updateRole(long id, RolesDTO dto) {
    Optional<RolesEntity> optionalRoles = getRolesById(id);
    if (optionalRoles.isEmpty()) throw new EntityNotFoundException("role not found!");

    RolesEntity rolesEntity = optionalRoles.get();
    rolesEntity.setRoleName(dto.getRoleName());

    RolesEntity savedRole = rolesRepository.save(optionalRoles.get());
    return RolesDTO.builder().roleName(savedRole.getRoleName()).id(savedRole.getId()).build();
  }
}
