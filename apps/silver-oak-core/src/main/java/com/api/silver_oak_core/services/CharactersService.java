package com.api.silver_oak_core.services;

import com.api.silver_oak_core.model.charaters.Characters;
import com.api.silver_oak_core.model.charaters.CharactersEntity;
import com.api.silver_oak_core.model.charaters.CharactersResponseDTO;
import com.api.silver_oak_core.model.classes.ClassesRegistry;
import com.api.silver_oak_core.repositories.CharactersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharactersService {
  private final CharactersRepository characterRepository;

  public List<CharactersResponseDTO> getCharacters() {
    List<CharactersEntity> characters = this.characterRepository.findAll();

    return characters.stream().map(character -> CharactersResponseDTO.builder()
          .id(character.getId())
          .life(character.getLife())
          .maxLife(character.getMaxLife())
          .damage(character.getDamage())
          .level(character.getLevel())
          .experience(character.getExperience())
          .characterClass(ClassesRegistry.getByName(character.getClassName()))
          .build()
    ).toList();
  }

  public Optional<CharactersResponseDTO> getCharacterEntity(Long id) {
    return this.characterRepository.findById(id).map(character -> CharactersResponseDTO.builder()
        .id(character.getId())
        .life(character.getLife())
        .maxLife(character.getMaxLife())
        .damage(character.getDamage())
        .level(character.getLevel())
        .experience(character.getExperience())
        .characterClass(ClassesRegistry.getByName(character.getClassName()))
        .build()
      );
  }

public Optional<Characters> getCharacter(Long id) {
  return this.characterRepository.findById(id).map(character ->
      new Characters(
        character.getId(),
        character.getLife(),
        character.getMaxLife(),
        character.getDamage(),
        character.getLevel(),
        character.getExperience(),
        ClassesRegistry.getByName(character.getClassName())
      )
  );
}

  public CharactersResponseDTO saveCharacter(CharactersEntity character) {
    CharactersEntity saved = this.characterRepository.save(character);

    return CharactersResponseDTO.builder()
      .id(saved.getId())
      .maxLife(saved.getMaxLife())
      .life(saved.getLife())
      .damage(saved.getDamage())
      .level(saved.getLevel())
      .experience(saved.getExperience())
      .characterClass(ClassesRegistry.getByName(saved.getClassName()))
      .build();
  }

  public void deleteCharacter(Long id) {
    if (this.getCharacter(id).isEmpty()) return;
    this.characterRepository.deleteById(id);
  }
}
