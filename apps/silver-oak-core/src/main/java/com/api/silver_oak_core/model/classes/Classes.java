package com.api.silver_oak_core.model.classes;

import com.api.silver_oak_core.model.weapons.WeaponType;
import java.util.List;

public abstract class Classes implements Cloneable {
  private final String name;

  protected Classes(String name) {
    this.name = name;
  }

  public abstract List<WeaponType> getUsableWeapons();

  @Override
  public Classes clone() throws CloneNotSupportedException {
    try {
      return (Classes) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new CloneNotSupportedException();
    }
  }
}
