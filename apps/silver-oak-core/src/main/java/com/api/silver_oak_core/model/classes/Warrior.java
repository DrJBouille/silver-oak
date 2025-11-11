package com.api.silver_oak_core.model.classes;

import com.api.silver_oak_core.model.weapons.WeaponType;

import java.util.List;

public class Warrior extends Classes {
  public Warrior() {
    super("Warrior");
  }

  @Override
  public List<WeaponType> getUsableWeapons() {
    return List.of(WeaponType.LIGHT, WeaponType.HEAVY, WeaponType.REACH, WeaponType.THROWN);
  }
}
