package com.api.silver_oak_core.repositories;

import com.api.silver_oak_core.model.users.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
  Optional<UsersEntity> findByUsernameAndIsEnabledTrue(String username);
}
