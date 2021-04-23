package com.devsarg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class GetMovieBySearchDto {
	private String title;
	private String description;
	private String releaseDate;
	private String genreIds;
	
}
