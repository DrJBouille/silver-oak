package com.api.silver_oak_core.model.arena;

import com.api.silver_oak_core.model.characters.Characters;
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

  public void startSimulation() {
    while (!isFinished) {
      attack();
    }
  }

  public void attack() {
    Characters attacker = this.isCharacter1Turn ? this.character1 : this.character2;
    Characters target = this.isCharacter1Turn ? this.character2 : this.character1;

    target.setLife(target.getLife() - attacker.attack());

    if (character2.getLife() <= 0) {
      this.isFinished = true;
      this.doesCharacter1Win = this.isCharacter1Turn;
    }

    this.isCharacter1Turn = !this.isCharacter1Turn;
  }
}
