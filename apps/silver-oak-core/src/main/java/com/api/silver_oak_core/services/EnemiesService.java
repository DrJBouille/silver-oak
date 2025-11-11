package com.api.silver_oak_core.services;

import com.api.silver_oak_core.model.charaters.Characters;
import com.api.silver_oak_core.model.charaters.enemies.Goblin;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class EnemiesService {
  private static final Map<String, Supplier<Characters>> ENEMIES = new HashMap<>();

  static {
    ENEMIES.put("Goblin", Goblin::new);
  }

  public Characters getByName(String name) throws CloneNotSupportedException {
    Supplier<Characters> supplier = ENEMIES.get(name);

    if (supplier == null) throw new RuntimeException("Enemy not found: " + name);
    return supplier.get().clone();
  }

  public Map<String, Supplier<Characters>> getEnemies() {
    return ENEMIES;
  }
}
