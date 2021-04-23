package com.devsarg.controller;

import com.devsarg.dto.GetMovieResponseDto;
import com.devsarg.dto.GetMovieBySearchDto;
import com.devsarg.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

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
    
    // Get all genres availables.
    @GetMapping("/genre")
    public JsonNode getMoviesGenre() {
    	return apiService.getMoviesGenre();
    }
    
    // Filter movies by genre
    @GetMapping("/genre/{genre}")
    public List<GetMovieBySearchDto> getMovieByGenre(@NonNull @PathVariable(value = "genre") String genre) {
    	return apiService.getMovieByGenre(genre);
    }

    // Pass a text query to search. This value should be URI encoded.
    @GetMapping("/search/{text}")
    public List<GetMovieBySearchDto> searchMovie (@NonNull @PathVariable(value = "text") String queryText) {
    	return apiService.searchMovieByQueryText(queryText);
    }
    
}
