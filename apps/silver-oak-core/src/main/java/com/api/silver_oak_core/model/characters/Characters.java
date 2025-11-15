package com.api.silver_oak_core.model.characters;

import com.api.silver_oak_core.model.classes.Classes;
import com.api.silver_oak_core.model.weapons.Weapon;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Characters {
  private long id;
  private int maxLife;
  private int life;
  private int damage;
  private int level;
  private int experience;
  private Classes characterClass;
  private Weapon weapon;

  public int attack() {
    int weaponDamage = weapon.getDamage();
    System.out.println(weaponDamage + " " + this.damage);
    return this.damage + weaponDamage;
  }
}
