package com.api.silver_oak_core.model.charaters;

import com.api.silver_oak_core.model.classes.Classes;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Characters implements Cloneable {
  private long id;
  private int maxLife;
  private int life;
  private int damage;
  private int level;
  private int experience;
  private Classes characterClass;

  public int attack() {
    return this.damage;
  };

  @Override
  public Characters clone() throws CloneNotSupportedException {
    try {
      Characters clone = (Characters) super.clone();
      clone.characterClass = this.characterClass != null ? this.characterClass.clone() : null;
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new CloneNotSupportedException();
    }
  }
}
