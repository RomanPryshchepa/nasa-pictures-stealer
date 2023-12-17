package com.bobocode.restApiDemo.service;

import com.bobocode.restApiDemo.entity.Photos;
import com.bobocode.restApiDemo.entity.Picture;
import com.bobocode.restApiDemo.repository.NasaRepository;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NasaService {

  @Value("${nasaUrl}")
  private String nasaUrl;

  @Value("${nasaKey}")
  private String nasaKey;

  private final RestTemplate restTemplate;

  private final NasaRepository nasaRepository;

  public NasaService(RestTemplate restTemplate, NasaRepository nasaRepository) {
    this.restTemplate = restTemplate;
    this.nasaRepository = nasaRepository;
  }

  public void stealNasaPictures(Integer sol) {
    URI uri = UriComponentsBuilder.fromHttpUrl(nasaUrl)
        .queryParam("sol", sol)
        .queryParam("api_key", nasaKey)
        .build().toUri();
    Photos photosResponse = restTemplate.getForObject(uri, Photos.class);
    if (photosResponse != null) {
      List<Picture> pictures = photosResponse.getPictures();
      nasaRepository.saveAllPictures(pictures);
    }
  }
}
