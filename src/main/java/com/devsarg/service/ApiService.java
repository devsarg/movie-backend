package com.devsarg.service;

import com.devsarg.dto.GetMovieResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ApiService {

    // base_url + resource + ?api_key=<<api_key>> + *
    @Value("${api.movie.base.url}")
    private String baseUrl;
    @Value("${api.movie.api.key}")
    private String apiKey;
    @Value("${api.movie.lenguage}")
    private String lenguage;

    @Autowired
    private RestTemplate restTemplate;

    public GetMovieResponseDto findById(String id) {
        log.info("[ApiService]:Init find by id: {}", id);

        String url = baseUrl + "movie/" + id + "?api_key=" + apiKey + lenguage;

        JsonNode apiResponse = restTemplate.getForEntity(url, JsonNode.class).getBody();

        GetMovieResponseDto getMovieResponseDto = buildGetMovieResponse(apiResponse);

        return getMovieResponseDto;
    }

    private GetMovieResponseDto buildGetMovieResponse(JsonNode apiResponse) {
       return new GetMovieResponseDto().builder()
                .name(apiResponse.get("original_title").asText())
                .resmume(apiResponse.get("overview").asText())
                .build();
    }

}
