package com.devsarg.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.devsarg.service.ApiService;

/**
Standard:
	GET: /devargs/movie-app
	POST: /movie/add-entity
 */

@RestController
@RequestMapping("/devsarg/movie-app")
public class MovieController {
	
	// base_url + resource + ?api_key=<<api_key>> + *
	@Value("${api.movie.base.url}")
	private String baseUrl;
	@Value("${api.movie.api.key}")
	private String apiKey;
	@Value("${api.movie.lenguage}")
	private String lenguage;
	
	private RestTemplate restTemplate = new RestTemplate();
	// 
	@GetMapping(path="/movie", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> getMovieById (@NonNull @RequestParam("id") String id) {

		String url = baseUrl + "movie/"+ id + "?api_key=" + apiKey + lenguage;
 		
		System.out.println(url);
		try {
			ResponseEntity<String> rspApi = restTemplate.getForEntity(url, String.class);
			return rspApi;
		} catch (HttpClientErrorException e) {
			System.out.println(e.getMessage());
			JSONObject errorResponse = new JSONObject();
			errorResponse.append("error", e.getMessage());

			return new ResponseEntity<String>(errorResponse.toString(), HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	// https://api.themoviedb.org/3/configuration/languages?api_key=<<api_key>>
	
}
