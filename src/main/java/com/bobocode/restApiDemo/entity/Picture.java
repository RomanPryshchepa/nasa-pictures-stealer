package com.bobocode.restApiDemo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Picture {
  @JsonProperty("id")
  private Integer nasaId;

  @JsonProperty("img_src")
  private String imgSrc;

  @JsonProperty("camera")
  private Camera camera;

}
