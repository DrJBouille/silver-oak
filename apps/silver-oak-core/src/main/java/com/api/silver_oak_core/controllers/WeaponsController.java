package com.api.silver_oak_core.controllers;

import com.api.silver_oak_core.model.weapons.Weapon;
import com.api.silver_oak_core.registries.WeaponsRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/weapons")
@RequiredArgsConstructor
public class WeaponsController {
  private WeaponsRegistry weaponsRegistry;

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping("/names")
  ResponseEntity<Set<String>> getWeaponsNames() {
    return ResponseEntity.ok(weaponsRegistry.getNames());
  }

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping()
  ResponseEntity<List<Weapon>> getWeapons() {
    return ResponseEntity.ok(weaponsRegistry.getWeapons());
  }
}
