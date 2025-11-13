package com.api.silver_oak_core.registries;

import com.api.silver_oak_core.model.weapons.heavy.Sword;
import com.api.silver_oak_core.model.weapons.Weapon;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@Service
public class WeaponsRegistry {
  private static final Map<String, Supplier<Weapon>> WEAPONS = new HashMap<>();

  static {
    WEAPONS.put("sword", Sword::new);
  }

  public Weapon getByName(String name) throws CloneNotSupportedException {
    Supplier<Weapon> supplier = WEAPONS.get(name);

    if (supplier == null) throw new RuntimeException("Weapon not found: " + name);
    return supplier.get().clone();
  }

  public Set<String> getNames() {
    return WEAPONS.keySet();
  }
}
