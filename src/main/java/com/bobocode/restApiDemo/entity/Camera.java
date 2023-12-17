package com.bobocode.restApiDemo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Camera {
  @JsonProperty("id")
  private Integer nasaId;

  @JsonProperty("name")
  private String name;
}
