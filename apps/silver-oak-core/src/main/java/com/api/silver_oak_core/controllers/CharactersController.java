package com.api.silver_oak_core.controllers;

import com.api.silver_oak_core.model.characters.Characters;
import com.api.silver_oak_core.model.characters.CharactersEntity;
import com.api.silver_oak_core.model.characters.CharactersRequestDTO;
import com.api.silver_oak_core.model.classes.Classes;
import com.api.silver_oak_core.model.users.UsersEntity;
import com.api.silver_oak_core.model.weapons.Weapon;
import com.api.silver_oak_core.registries.CharactersRegistry;
import com.api.silver_oak_core.registries.ClassesRegistry;
import com.api.silver_oak_core.registries.WeaponsRegistry;
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
import java.util.Set;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharactersController {
  private final CharactersService charactersService;
  private final UsersService usersService;
  private final ClassesRegistry classesRegistry;
  private final WeaponsRegistry weaponsRegistry;
  private final CharactersRegistry charactersRegistry;

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping
  ResponseEntity<List<Characters>> getCharacters() {
    try {
      return ResponseEntity.ok(this.charactersService.getCharacters());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping("/{id}")
  ResponseEntity<Characters> getCharacter(@PathVariable Long id) {
    try {
      Optional<Characters> character = this.charactersService.getCharacterEntity(id);
      return character.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

  }

  @PreAuthorize("hasAuthority('USER')")
  @PostMapping()
  ResponseEntity<Characters> saveCharacter(@RequestBody CharactersRequestDTO charactersRequestDTO, Authentication authentication) {
    try {
      Classes characterClass = classesRegistry.getClass(charactersRequestDTO.getClassName());
      Weapon weapon = weaponsRegistry.getWeapon(charactersRequestDTO.getWeapon());
      if (!characterClass.getUsableWeapons().contains(weapon.getWeaponType())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

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

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping("/defaults/{name}")
  ResponseEntity<Characters> getDefaultCharacters(@PathVariable String name) {
    try {
      return ResponseEntity.ok(this.charactersRegistry.getCharacter(name));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping("/defaults")
  ResponseEntity<List<Characters>> getDefaultCharacters() {
    try {
      return ResponseEntity.ok(this.charactersRegistry.getCharacters());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping("/defaults-names")
  ResponseEntity<Set<String>> getDefaultCharactersNames() {
    try {
      return ResponseEntity.ok(this.charactersRegistry.getNames());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
