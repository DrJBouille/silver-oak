package com.api.silver_oak_core.model.arena;

import com.api.silver_oak_core.model.charaters.Characters;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Arena {
  private Characters character1;
  private Characters character2;

  private Boolean doesCharacter1Win = false;
  private Boolean isFinished = false;
  private Boolean isCharacter1Turn = true;

  public Arena(Characters character1, Characters character2) {
    this.character1 = character1;
    this.character2 = character2;
  }

  public void startSimulation() {
    while (!isFinished) {
      if (isCharacter1Turn) character1attack();
      else character2attack();
    }
  }

  public Boolean character1attack() {
    if (!isCharacter1Turn) return false;
    character2.setLife(this.character2.getLife() - character1.attack());

    if (character2.getLife() <= 0) {
      this.isFinished = true;
      this.doesCharacter1Win = true;
    }

    this.isCharacter1Turn = false;
    return true;
  }

  public Boolean character2attack() {
    if (isCharacter1Turn) return false;
    character1.setLife(this.character1.getLife() - character2.attack());

    if (character1.getLife() <= 0) {
      this.isFinished = true;
      this.doesCharacter1Win = false;
    }

    this.isCharacter1Turn = true;
    return true;
  }
}
