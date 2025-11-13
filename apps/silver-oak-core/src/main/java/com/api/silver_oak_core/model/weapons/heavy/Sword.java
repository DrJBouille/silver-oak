package com.api.silver_oak_core.model.weapons.heavy;

import com.api.silver_oak_core.model.weapons.Weapon;
import com.api.silver_oak_core.model.weapons.WeaponType;

public class Sword extends Weapon {
  public Sword() {
    super("Sword", 6, 1, WeaponType.HEAVY);
  }
}
