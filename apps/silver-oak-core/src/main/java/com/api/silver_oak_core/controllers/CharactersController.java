package com.api.silver_oak_core.controllers;

import com.api.silver_oak_core.model.charaters.CharactersEntity;
import com.api.silver_oak_core.model.charaters.CharactersRequestDTO;
import com.api.silver_oak_core.model.charaters.CharactersResponseDTO;
import com.api.silver_oak_core.model.classes.ClassesRegistry;
import com.api.silver_oak_core.model.users.UsersEntity;
import com.api.silver_oak_core.services.CharactersService;
import com.api.silver_oak_core.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharactersController {
  private final CharactersService charactersService;
  private final UsersService usersService;


  @PreAuthorize("hasAuthority('USER')")
  @GetMapping
  ResponseEntity<List<CharactersResponseDTO>> getCharacters() {
    try {
      return ResponseEntity.ok(this.charactersService.getCharacters());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping("/{id}")
  ResponseEntity<CharactersResponseDTO> getCharacter(@PathVariable Long id) {
    try {
      Optional<CharactersResponseDTO> character = this.charactersService.getCharacterEntity(id);
      return character.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

  }

  @PreAuthorize("hasAuthority('USER')")
  @PostMapping()
  ResponseEntity<CharactersResponseDTO> saveCharacter(@RequestBody CharactersRequestDTO charactersRequestDTO, Authentication authentication) {
    try {
      ClassesRegistry.getByName(charactersRequestDTO.getClassName());

      String username = authentication.getName();
      UsersEntity user = usersService.getUserByUsername(username);

      CharactersEntity charactersEntity = new CharactersEntity();
      charactersEntity.setClassName(charactersRequestDTO.getClassName());
      charactersEntity.setUser(user);

      return ResponseEntity.ok(charactersService.saveCharacter(charactersEntity));
    } catch (Exception e)  {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
