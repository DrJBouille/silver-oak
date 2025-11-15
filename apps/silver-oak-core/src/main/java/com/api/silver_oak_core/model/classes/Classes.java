package com.api.silver_oak_core.model.classes;

import com.api.silver_oak_core.model.weapons.WeaponType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public abstract class Classes implements Cloneable {
  public final String name;
  public abstract List<WeaponType> getUsableWeapons();

  @Override
  public Classes clone() {
    try {
      return (Classes) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
