package com.api.silver_oak_core.model.tokens;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity(name = "tokens")
public class TokensEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String username;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(name = "expiry_date")
  private Instant expiryDate;

  private boolean valid;
}
