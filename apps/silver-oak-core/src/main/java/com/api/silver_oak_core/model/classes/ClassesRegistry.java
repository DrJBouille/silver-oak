package com.api.silver_oak_core.model.classes;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@Component
public class ClassesRegistry {
  private static final Map<String, Supplier<Classes>> CLASSES = new HashMap<>();

  static {
    CLASSES.put("Warrior", Warrior::new);
  }

  public static Classes getByName(String className) {
    Supplier<Classes> supplier = CLASSES.get(className);

    if (supplier == null) throw new RuntimeException("Class not found: " + className);
    return supplier.get();
  }

  public static Set<String> getClasses() {
    return CLASSES.keySet();
  }
}
