package com.devsarg.controller;

import com.devsarg.dto.GetMovieResponseDto;
import com.devsarg.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Standard:
 * GET: /devargs/movie-app
 * POST: /movie/add-entity
 */

@RestController
@RequestMapping("/api/v1/movies")
@Slf4j
public class MovieController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/{id}")
    public GetMovieResponseDto getMovieById(@NonNull @PathVariable(value = "id") String id) {
        return apiService.findById(id);
    }

}
