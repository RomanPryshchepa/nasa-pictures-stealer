package com.bobocode.restApiDemo.controller;


import com.bobocode.restApiDemo.service.NasaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NasaController {
  private final NasaService nasaService;

  public NasaController(NasaService nasaService) {
    this.nasaService = nasaService;
  }

  @PostMapping("/pictures/steal")
  public void stealNasaPictures(@RequestBody SolDTO solDTO) {
    nasaService.stealNasaPictures(solDTO.sol());
  }
}
