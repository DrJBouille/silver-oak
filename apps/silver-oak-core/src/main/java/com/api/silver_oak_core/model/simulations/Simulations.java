package com.api.silver_oak_core.model.simulations;


import com.api.silver_oak_core.model.arena.Arena;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Simulations {
  private final Arena arena;
  private int nbOfAttack = 0;
  private final List<AttackResults> attackResultsSet = new ArrayList<>();

  public void addAttackResults(AttackResults attackResults) {
    this.nbOfAttack++;
    this.attackResultsSet.add(attackResults);
  }
}
