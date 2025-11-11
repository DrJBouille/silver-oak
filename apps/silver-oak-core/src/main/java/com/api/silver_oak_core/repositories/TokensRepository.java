package com.api.silver_oak_core.repositories;

import com.api.silver_oak_core.model.tokens.TokensEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokensRepository extends JpaRepository<TokensEntity, Long> {
  Optional<TokensEntity> findTokenByTokenAndValidTrue(String token);
}
