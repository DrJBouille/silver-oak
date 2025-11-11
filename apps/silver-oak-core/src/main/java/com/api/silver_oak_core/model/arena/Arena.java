package com.api.silver_oak_core.model.arena;

import com.api.silver_oak_core.model.charaters.Characters;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Arena {
  private Characters player;
  private Characters enemy;

  private Boolean doesPlayerWin = false;
  private Boolean isFinished = false;
  private Boolean isPlayerTurn = true;

  public Arena(Characters player, Characters enemy) {
    this.player = player;
    this.enemy = enemy;
  }

  public Boolean attack() {
    if (!isPlayerTurn) return false;
    enemy.setLife(this.enemy.getLife() - player.attack());

    if (enemy.getLife() <= 0) {
      this.isFinished = true;
      this.doesPlayerWin = true;
    }

    this.isPlayerTurn = false;
    return true;
  }

  public Boolean enemyAttack() {
    if (isPlayerTurn) return false;
    player.setLife(this.player.getLife() - enemy.attack());

    if (player.getLife() <= 0) {
      this.isFinished = true;
      this.doesPlayerWin = false;
    }

    this.isPlayerTurn = true;
    return true;
  }
}
