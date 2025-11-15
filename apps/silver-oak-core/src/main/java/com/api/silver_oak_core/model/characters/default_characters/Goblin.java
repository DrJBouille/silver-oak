package com.api.silver_oak_core.model.characters.default_characters;

import com.api.silver_oak_core.model.characters.Characters;
import com.api.silver_oak_core.model.classes.Warrior;
import com.api.silver_oak_core.model.weapons.heavy.Sword;

public class Goblin extends Characters {

  public Goblin() {
    super(0L, 10, 10, 2, 1, 0, new Warrior(), new Sword());
  }

  @Override
  public int attack() {
    return this.getDamage();
  }
}
