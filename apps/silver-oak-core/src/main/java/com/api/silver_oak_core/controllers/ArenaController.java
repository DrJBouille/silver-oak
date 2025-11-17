package com.api.silver_oak_core.controllers;

import com.api.silver_oak_core.model.arena.Arena;
import com.api.silver_oak_core.model.arena.ArenaRequestDTO;
import com.api.silver_oak_core.model.characters.Characters;
import com.api.silver_oak_core.model.simulations.Simulations;
import com.api.silver_oak_core.model.simulations.SimulationsRequestDTO;
import com.api.silver_oak_core.model.simulations.TreeSimulation;
import com.api.silver_oak_core.registries.CharactersRegistry;
import com.api.silver_oak_core.services.ArenaService;
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
  private final CharactersRegistry charactersRegistry;
  private final ArenaService arenaService;

  private final static Map<Long, Arena> PLAYER_TO_ARENA = new HashMap<>();


  @PreAuthorize("hasAuthority('USER')")
  @PostMapping
  ResponseEntity<Arena> initiateArena(@RequestBody ArenaRequestDTO arenaRequestDTO) {
    try {
      Optional<Characters> character = charactersService.getCharacter(arenaRequestDTO.getCharacterId());
      if (character.isEmpty()) return ResponseEntity.notFound().build();
      if (PLAYER_TO_ARENA.containsKey(character.get().getId())) return ResponseEntity.badRequest().build();

      Characters player = character.get();
      Characters enemy = charactersRegistry.getCharacter(arenaRequestDTO.getType());

      Arena arena = new Arena(player, enemy);

      PLAYER_TO_ARENA.put(arenaRequestDTO.getCharacterId(), arena);

      return ResponseEntity.ok(arena);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PreAuthorize("hasAuthority('USER')")
  @PostMapping("/simple-simulation")
  ResponseEntity<Simulations> startSimpleSimulations(@RequestBody SimulationsRequestDTO simulationsRequestDTO) {
    try {
      Characters character1 = charactersRegistry.getCharacter(simulationsRequestDTO.firstCharactersName());
      Characters character2 = charactersRegistry.getCharacter(simulationsRequestDTO.firstCharactersName());

      Arena arena = new Arena(character1, character2);
      Simulations simulation = arena.startSimulation();

      return ResponseEntity.ok(simulation);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PreAuthorize("hasAuthority('USER')")
  @PostMapping("/multi-simulation")
  ResponseEntity<List<Simulations>> startMultipleSimulations() {
    try {
      List<Simulations> simulations = arenaService.runSimulations(1000);
      return ResponseEntity.ok(simulations);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PreAuthorize("hasAuthority('USER')")
  @PostMapping("/tree-simulation")
  ResponseEntity<TreeSimulation> startTreeSimulations(@RequestBody SimulationsRequestDTO simulationsRequestDTO) {
    try {
      TreeSimulation treeSimulation = arenaService.runTreeSimulation(simulationsRequestDTO.firstCharactersName(), simulationsRequestDTO.secondCharactersName());
      return ResponseEntity.ok(treeSimulation);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping("/types")
  ResponseEntity<Set<String>> getAllTypes() {
    return ResponseEntity.ok(charactersRegistry.getNames());
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

    arena.attack();

    if (arena.isFinished()) PLAYER_TO_ARENA.remove(id);
    return ResponseEntity.ok(arena);
  }
}
