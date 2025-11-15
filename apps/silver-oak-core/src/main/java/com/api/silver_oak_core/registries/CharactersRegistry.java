package com.api.silver_oak_core.registries;

import com.api.silver_oak_core.model.characters.Characters;
import com.api.silver_oak_core.model.characters.default_characters.Goblin;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@Service
public class CharactersRegistry {
  private static final Map<String, Supplier<Characters>> ENEMIES = new HashMap<>();

  static {
    ENEMIES.put("goblin", Goblin::new);
  }

  public Characters getCharacter(String name) throws CloneNotSupportedException {
    Supplier<Characters> supplier = ENEMIES.get(name);

    if (supplier == null) throw new RuntimeException("Enemy not found: " + name);
    return supplier.get();
  }

  public Set<String> getNames() {
    return ENEMIES.keySet();
  }
}
