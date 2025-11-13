package com.api.silver_oak_core.controllers;

import com.api.silver_oak_core.model.arena.Arena;
import com.api.silver_oak_core.model.arena.ArenaRequestDTO;
import com.api.silver_oak_core.model.charaters.Characters;
import com.api.silver_oak_core.registries.EnemiesRegistry;
import com.api.silver_oak_core.services.CharactersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/arenas")
@RequiredArgsConstructor
public class ArenaController {
  private final CharactersService charactersService;
  private final EnemiesRegistry enemiesRegistry;

  private final static Map<Long, Arena> PLAYER_TO_ARENA = new HashMap<>();


  @PreAuthorize("hasAuthority('USER')")
  @PostMapping
  ResponseEntity<Arena> initiateArena(@RequestBody ArenaRequestDTO arenaRequestDTO) {
    try {
      Optional<Characters> character = charactersService.getCharacter(arenaRequestDTO.getCharacterId());
      if (character.isEmpty()) return ResponseEntity.notFound().build();
      if (PLAYER_TO_ARENA.containsKey(character.get().getId())) return ResponseEntity.badRequest().build();

      Characters player = character.get();
      Characters enemy = enemiesRegistry.getByName(arenaRequestDTO.getType());

      Arena arena = new Arena(player, enemy);

      PLAYER_TO_ARENA.put(arenaRequestDTO.getCharacterId(), arena);

      return ResponseEntity.ok(arena);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping("/types")
  ResponseEntity<Set<String>> getAllTypes() {
    return ResponseEntity.ok(enemiesRegistry.getNames());
  }

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping("/{id}")
  ResponseEntity<Arena> getArenas(@PathVariable long id) {
    if (PLAYER_TO_ARENA.containsKey(id)) return ResponseEntity.ok(PLAYER_TO_ARENA.get(id));
    else return ResponseEntity.notFound().build();
  }

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping("/attack/{id}")
  ResponseEntity<Arena> attack(@PathVariable long id) {
    if (!PLAYER_TO_ARENA.containsKey(id)) return ResponseEntity.notFound().build();
    Arena arena = PLAYER_TO_ARENA.get(id);

    if (!arena.attack()) return ResponseEntity.badRequest().build();

    if (arena.getIsFinished()) PLAYER_TO_ARENA.remove(id);
    return ResponseEntity.ok(arena);
  }

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping("/enemy-attack/{id}")
  ResponseEntity<Arena> enemyAttack(@PathVariable long id) {
    if (!PLAYER_TO_ARENA.containsKey(id)) return ResponseEntity.notFound().build();
    Arena arena = PLAYER_TO_ARENA.get(id);

    if (!arena.enemyAttack()) return ResponseEntity.badRequest().build();

    if (arena.getIsFinished()) PLAYER_TO_ARENA.remove(id);
    return ResponseEntity.ok(arena);
  }
}
