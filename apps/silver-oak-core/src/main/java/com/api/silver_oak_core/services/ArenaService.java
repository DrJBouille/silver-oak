package com.api.silver_oak_core.services;

import com.api.silver_oak_core.model.arena.Arena;
import com.api.silver_oak_core.model.characters.Characters;
import com.api.silver_oak_core.model.simulations.AttackResults;
import com.api.silver_oak_core.model.simulations.Node;
import com.api.silver_oak_core.model.simulations.Simulations;
import com.api.silver_oak_core.model.simulations.TreeSimulation;
import com.api.silver_oak_core.registries.CharactersRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class ArenaService {
  private final CharactersRegistry charactersRegistry;

  ExecutorService executor = Executors.newFixedThreadPool(8);
  CompletionService<Simulations> completionService = new ExecutorCompletionService<>(executor);

  public List<Simulations> runSimulations(int ngOfSimulations) throws InterruptedException, ExecutionException {
    for (int i =0; i < ngOfSimulations; i++) {
      Characters character1 = charactersRegistry.getCharacter("goblin");
      Characters character2 = charactersRegistry.getCharacter("goblin");
      Arena arena = new Arena(character1, character2);

      completionService.submit(arena::startSimulation);
    }

    List<Simulations> simulations = new ArrayList<>();
    for (int i =0; i < ngOfSimulations; i++) {
      simulations.add(completionService.take().get());
    }

    return simulations;
  }

  public TreeSimulation runTreeSimulation(String firstCharactersName, String secondCharactersName) {
    try {
      Characters character1 = charactersRegistry.getCharacter(firstCharactersName);
      Characters character2 = charactersRegistry.getCharacter(secondCharactersName);
      Arena arena = new Arena(character1, character2);

      Node root = new Node(new AttackResults(0, 0, 0, false));
      expend(root, arena);

      return new TreeSimulation(List.of(character1, character2), root);
    } catch (Exception e) {
      throw new RuntimeException("Error in runTreeSimulation");
    }
  }

  public void expend(Node node, Arena arena) {
    int damageRange = arena.isCharacter1Turn() ? arena.getCharacter1().getWeapon().getDamageRange() : arena.getCharacter2().getWeapon().getDamageRange();

    for (int diceValue = 1; diceValue <= damageRange; diceValue++) {
      Arena clonedArena = arena.copy();
      Characters attacker = clonedArena.isCharacter1Turn() ? clonedArena.getCharacter1() : clonedArena.getCharacter2();
      Characters target = clonedArena.isCharacter1Turn() ? clonedArena.getCharacter2() : clonedArena.getCharacter1();

      AttackResults result = clonedArena.specificAttack(attacker, target, diceValue);

      Node child = new Node(result);
      node.getChildren().add(child);

      if (!result.isFatalBLow()) {
        expend(child, clonedArena);
      }
    }
  }
}
