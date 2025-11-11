package com.api.silver_oak_core.model.charaters.enemies;

import com.api.silver_oak_core.model.charaters.Characters;
import com.api.silver_oak_core.model.classes.Warrior;

public class Goblin extends Characters {

  public Goblin() {
    super(0L, 10, 10, 2, 1, 0, new Warrior());
  }

  @Override
  public int attack() {
    return this.getDamage();
  }
}
