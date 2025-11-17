package com.api.silver_oak_core.registries;

import com.api.silver_oak_core.model.characters.Characters;
import com.api.silver_oak_core.model.characters.default_characters.Goblin;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@Service
public class CharactersRegistry {
  private static final Map<String, Supplier<Characters>> DEFAULT_CHARACTERS = new HashMap<>();

  static {
    DEFAULT_CHARACTERS.put("goblin", Goblin::new);
  }

  public Characters getCharacter(String name) throws RuntimeException {
    Supplier<Characters> supplier = DEFAULT_CHARACTERS.get(name);

    if (supplier == null) throw new RuntimeException("Enemy not found: " + name);
    return supplier.get();
  }

  public List<Characters> getCharacters() {
    return DEFAULT_CHARACTERS.values().stream().map(Supplier::get).toList();
  }

  public Set<String> getNames() {
    return DEFAULT_CHARACTERS.keySet();
  }
}
