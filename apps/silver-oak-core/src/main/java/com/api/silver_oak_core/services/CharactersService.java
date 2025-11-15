package com.api.silver_oak_core.services;

import com.api.silver_oak_core.model.charaters.Characters;
import com.api.silver_oak_core.model.charaters.CharactersEntity;
import com.api.silver_oak_core.model.charaters.CharactersResponseDTO;
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

  public List<CharactersResponseDTO> getCharacters() {
    List<CharactersEntity> characters = this.characterRepository.findAll();

    return characters.stream().map(this::mapToCharacter).toList();
  }

  public Optional<CharactersResponseDTO> getCharacterEntity(Long id) {
    return this.characterRepository.findById(id).map(this::mapToCharacter);
  }

public Optional<Characters> getCharacter(Long id) {
  return this.characterRepository.findById(id).map(character ->
      Characters.builder()
        .id(character.getId())
        .maxLife(character.getMaxLife())
        .life(character.getLife())
        .experience(character.getExperience())
        .level(character.getLevel())
        .damage(character.getDamage())
        .characterClass(classesRegistry.getClass(character.getClassName()))
        .weapon(weaponsRegistry.getWeapon(character.getWeaponName()))
        .build()
  );
}

  public CharactersResponseDTO saveCharacter(CharactersEntity character) {
    return mapToCharacter(this.characterRepository.save(character));
  }

  public void deleteCharacter(Long id) {
    if (this.getCharacter(id).isEmpty()) return;
    this.characterRepository.deleteById(id);
  }

  private CharactersResponseDTO mapToCharacter(CharactersEntity character) {
    return CharactersResponseDTO.builder()
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
