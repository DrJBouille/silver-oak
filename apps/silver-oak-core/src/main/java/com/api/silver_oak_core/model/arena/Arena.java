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
    int baseDamage = attacker.getDamage() + attacker.getWeapon().getAdditionalDamage();
    int diceDamage = damage - baseDamage;

    return applyDamage(target, damage, diceDamage, baseDamage);
  }

  public AttackResults specificAttack(Characters attacker, Characters target, int diceDamage) {
    if (diceDamage <= 0 || diceDamage > attacker.getWeapon().getDamageRange()) throw new RuntimeException("Value  out of range");

    int baseDamage = attacker.getDamage() + attacker.getWeapon().getAdditionalDamage();
    int damage = diceDamage + baseDamage;

    return applyDamage(target, damage, diceDamage, baseDamage);
  }

  private AttackResults applyDamage(Characters target, int damage, int diceDamage, int baseDamage) {
    target.setLife(target.getLife() - damage);

    AttackResults attackResult = new AttackResults(damage, diceDamage, baseDamage, this.isFinished);

    if (target.getLife() <= 0) {
      this.isFinished = true;
      this.doesCharacter1Win = this.isCharacter1Turn;
      return attackResult;
    }

    this.isCharacter1Turn = !this.isCharacter1Turn;

    return attackResult;
  }

  public Arena copy() {
    Characters character1Copy = new Characters(character1);
    Characters character2Copy = new Characters(character2);

    Arena cloned = new Arena(character1Copy, character2Copy);

    cloned.doesCharacter1Win = this.doesCharacter1Win;
    cloned.isFinished = this.isFinished;
    cloned.isCharacter1Turn = this.isCharacter1Turn;

    return cloned;
  }
}
