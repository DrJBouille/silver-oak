package com.api.silver_oak_core.model.arena;

import com.api.silver_oak_core.model.characters.Characters;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ArenaResponseDTO {
  private Characters player;
  private Characters enemy;

  private Boolean doesPlayerWin;
  private Boolean isFinished;
  private Boolean isPlayerTurn;
}
