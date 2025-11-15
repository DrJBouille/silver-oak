package com.api.silver_oak_core.registries;

import com.api.silver_oak_core.model.classes.Classes;
import com.api.silver_oak_core.model.classes.Warrior;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@Service
public class ClassesRegistry {
  private static final Map<String, Supplier<Classes>> CLASSES = new HashMap<>();

  static {
    CLASSES.put("warrior", Warrior::new);
  }

  public Classes getClass(String name) throws RuntimeException {
    Supplier<Classes> supplier = CLASSES.get(name);

    if (supplier == null) throw new RuntimeException("Class not found: " + name);
    return supplier.get();
  }

  public Set<String> getNames() {
    return CLASSES.keySet();
  }

  public List<Classes> getClasses() {
    return CLASSES.values().stream().map(Supplier::get).toList();
  }
}
