package com.api.silver_oak_core.model.arena;

import com.api.silver_oak_core.model.characters.Characters;
import com.api.silver_oak_core.model.simulations.AttackResults;
import com.api.silver_oak_core.model.simulations.Simulations;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Arena {
  private final Characters character1;
  private final Characters character2;

  private boolean doesCharacter1Win = false;
  private boolean isFinished = false;
  private boolean isCharacter1Turn = true;

  public Simulations startSimulation() {
    Simulations simulation = new Simulations(this);

    while (!isFinished) {
      simulation.addAttackResults(attack());
    }

    return simulation;
  }

  public AttackResults attack() {
    Characters attacker = this.isCharacter1Turn ? this.character1 : this.character2;
    Characters target = this.isCharacter1Turn ? this.character2 : this.character1;

    int damage = attacker.attack();
    int baseDamage = attacker.getDamage();
    int additionalDamage  = attacker.getWeapon().getAdditionalDamage();
    int diceDamage = damage - baseDamage - additionalDamage;

    target.setLife(target.getLife() - damage);

    AttackResults attackResult = new AttackResults(damage, diceDamage, baseDamage +  additionalDamage);

    if (target.getLife() <= 0) {
      this.isFinished = true;
      this.doesCharacter1Win = this.isCharacter1Turn;
      return attackResult;
    }

    this.isCharacter1Turn = !this.isCharacter1Turn;

    return attackResult;
  }
}
