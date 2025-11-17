package com.api.silver_oak_core.controllers;

import com.api.silver_oak_core.model.classes.Classes;
import com.api.silver_oak_core.registries.ClassesRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassesController {
  private final ClassesRegistry classesRegistry;

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping("/names")
  public ResponseEntity<Set<String>> getClassesRegistry() {
    return ResponseEntity.ok(classesRegistry.getNames());
  }

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping
  ResponseEntity<List<Classes>> getClasses() {
    return ResponseEntity.ok(classesRegistry.getClasses());
  }
}
