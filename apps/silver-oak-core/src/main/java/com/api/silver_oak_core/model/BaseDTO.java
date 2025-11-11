package com.api.silver_oak_core.model;

import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseDTO {
  @Null private LocalDateTime createdDateTime;

  @Null private LocalDateTime updatedDateTime;

  @Null private String createdBy;

  @Null private String updatedBy;
}
