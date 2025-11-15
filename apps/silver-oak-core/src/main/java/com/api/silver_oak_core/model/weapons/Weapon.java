package com.api.silver_oak_core.model.weapons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Random;


@Getter
@RequiredArgsConstructor
public abstract class Weapon implements Cloneable {
  private final Random random = new Random();

  private final String name;
  private final int damageRange;
  private final int additionalDamage;
  private final WeaponType weaponType;

  public int getDamage() {
    return random.nextInt(damageRange) + 1 + additionalDamage;
  }

  @Override
  public Weapon clone() {
    try {
      return  (Weapon) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
