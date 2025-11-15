package com.api.silver_oak_core.repositories;

import com.api.silver_oak_core.model.characters.CharactersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharactersRepository extends JpaRepository<CharactersEntity, Long> {
}
