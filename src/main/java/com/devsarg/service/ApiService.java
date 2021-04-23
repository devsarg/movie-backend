package com.devsarg.service;

import com.devsarg.dto.GetMovieResponseDto;
import com.devsarg.dto.GetMovieBySearchDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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
    
    public JsonNode getMoviesGenre() {
    	log.info("[ApiService]: Init getting genres" );
    	
    	String url = baseUrl + "genre/movie/list?api_key=" + apiKey + lenguage;
    	
    	JsonNode apiResponse = restTemplate.getForEntity(url, JsonNode.class).getBody();
    	JsonNode genres = apiResponse.path("genres");
    	
    	return genres;
    }
    
    /**
     * 
     * @param genre : id of genre to look for
     * @return
     */
    public List<GetMovieBySearchDto> getMovieByGenre(String genre) {
    	List<GetMovieBySearchDto> moviesFound = new ArrayList<GetMovieBySearchDto>();

    	log.info("[ApiService]: Init get movie by genre");
    	
    	String url = baseUrl + "discover/movie?api_key=" + apiKey + lenguage + 
    			"&sort_by=popularity.desc&include_adult=false&include_video=false&page=1" +
    			"&with_genres=" + genre;
		
    	JsonNode apiResponse = restTemplate.getForEntity(url, JsonNode.class).getBody();
    	JsonNode results = apiResponse.path("results");
    	if (results.isArray()) {
			for (JsonNode movie : results) {
				moviesFound.add(buildGetMovieBySearchDto(movie));
			}
		}
    	return moviesFound;
    }
    
    public List<GetMovieBySearchDto> searchMovieByQueryText(String queryText) {
    	List<GetMovieBySearchDto> moviesFound = new ArrayList<GetMovieBySearchDto>();
    	log.info("[ApiService]: Init search movie by query");
    	
    	String url = baseUrl + "search/movie?api_key=" + apiKey + lenguage + 
    			"&query=" + queryText + "&page=1&include_adult=false";
    	JsonNode apiResponse = restTemplate.getForEntity(url, JsonNode.class).getBody();
    	
    	JsonNode results = apiResponse.get("results");
    	
    	if (results.isArray()) {
			for (JsonNode movie : results) {
				moviesFound.add(buildGetMovieBySearchDto(movie));
			}
		}
    	return moviesFound;
    }
    
    /** 		Builders		**/
    
    private GetMovieBySearchDto buildGetMovieBySearchDto (JsonNode movie) {
    	// Getting genre_ids
    	String genreIdsStr = "";
    	JsonNode genreIds = movie.get("genre_ids");
    	for (JsonNode id : genreIds) {
			genreIdsStr += id + "|";
		}
    	return GetMovieBySearchDto.builder()
    			.title(movie.get("original_title").asText())
    			.description(movie.get("overview").asText())
    			.releaseDate(movie.get("release_date").asText())
    			.genreIds(genreIdsStr)
    			.build();
    }
    
    private GetMovieResponseDto buildGetMovieResponse(JsonNode apiResponse) {
	return GetMovieResponseDto.builder().name(apiResponse.get("original_title").asText())
			.resume(apiResponse.get("overview").asText()).build();
    }

}
