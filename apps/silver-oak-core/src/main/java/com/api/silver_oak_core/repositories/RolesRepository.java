package com.api.silver_oak_core.repositories;

import com.api.silver_oak_core.model.roles.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Long> {
  Optional<RolesEntity> findRolesEntityByRoleName(String roleName);
}
