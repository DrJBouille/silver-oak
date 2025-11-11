package com.api.silver_oak_core.model.charaters;

import com.api.silver_oak_core.model.users.UsersEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "characters")
@Entity
public class CharactersEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private long id;

  @Column(name = "max_life")
  private int maxLife = 10;

  private int life = 10;

  private int damage = 1;

  private int level = 1;

  private int experience = 0;

  @Column(name = "class_name")
  private String className;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UsersEntity user;
}
