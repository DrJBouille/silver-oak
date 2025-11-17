package com.api.silver_oak_core.model.weapons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@Getter
@RequiredArgsConstructor
public abstract class Weapon {
  @JsonIgnore
  private final Random random = new Random();

  private final String name;
  private final int damageRange;
  private final int additionalDamage;
  private final WeaponType weaponType;

  @JsonIgnore
  public int getDamage() {
    return random.nextInt(damageRange) + 1 + additionalDamage;
  }
}
