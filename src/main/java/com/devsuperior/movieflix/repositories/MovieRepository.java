package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("SELECT obj FROM Movie obj INNER JOIN obj.genre gen WHERE "
			+ "(COALESCE(:genre) IS NULL OR gen IN :genre) "
			+ "ORDER BY obj.title")
	Page<Movie> find(List<Genre> genre, Pageable pageable);

	@Query("SELECT obj FROM Movie obj JOIN FETCH obj.genre WHERE obj IN :movies")
	List<Movie> findMoviesWithGenre(List<Movie> movies);
}
