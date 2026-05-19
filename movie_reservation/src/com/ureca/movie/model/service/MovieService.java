package com.ureca.movie.model.service;

import java.util.List;

import com.ureca.user.model.dto.Movie;

public interface MovieService
{
	void add(Movie mov);

	void update(Movie mov);

	void remove(int movieId);

	Movie search(int movieId);

	List<Movie> searchAll();
}
