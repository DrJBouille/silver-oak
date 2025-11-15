package com.api.silver_oak_core.services;

import com.api.silver_oak_core.model.characters.Characters;
import com.api.silver_oak_core.model.characters.CharactersEntity;
import com.api.silver_oak_core.registries.ClassesRegistry;
import com.api.silver_oak_core.registries.WeaponsRegistry;
import com.api.silver_oak_core.repositories.CharactersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CharactersService {
  private final CharactersRepository characterRepository;
  private final ClassesRegistry classesRegistry;
  private final WeaponsRegistry weaponsRegistry;

  public List<Characters> getCharacters() {
    List<CharactersEntity> characters = this.characterRepository.findAll();

    return characters.stream().map(this::mapToCharacter).toList();
  }

  public Optional<Characters> getCharacterEntity(Long id) {
    return this.characterRepository.findById(id).map(this::mapToCharacter);
  }

public Optional<Characters> getCharacter(Long id) {
  return this.characterRepository.findById(id).map(this::mapToCharacter);
}

  public Characters saveCharacter(CharactersEntity character) {
    return mapToCharacter(this.characterRepository.save(character));
  }

  public void deleteCharacter(Long id) {
    if (this.getCharacter(id).isEmpty()) return;
    this.characterRepository.deleteById(id);
  }

  private Characters mapToCharacter(CharactersEntity character) {
    return Characters.builder()
      .id(character.getId())
      .maxLife(character.getMaxLife())
      .life(character.getLife())
      .damage(character.getDamage())
      .level(character.getLevel())
      .experience(character.getExperience())
      .characterClass(classesRegistry.getClass(character.getClassName()))
      .weapon(weaponsRegistry.getWeapon(character.getWeaponName()))
      .build();
  }
}
