package com.api.silver_oak_core.model.charaters;

import com.api.silver_oak_core.model.classes.Classes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CharactersResponseDTO {
  private long id;

  private int maxLife;

  private int life;

  private int damage;

  private int level;

  private int experience;

  private Classes characterClass;
}
