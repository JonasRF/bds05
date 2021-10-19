package com.devsuperior.movieflix.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.DTO.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private ReviewRepository repository;
	
	@Autowired
	private MovieRepository movieRepository;

	
	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {
		User user = authService.authenticated();
		
		Review entity = new Review();
		entity.setText(dto.getText());
		entity.setMovie(new Movie(dto.getMovieId(), null, null, null, null, null, null));
		entity.setUser(user);
		entity = repository.save(entity);
		return new ReviewDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public List<ReviewDTO> movieReviews(Long movieId){
	User user = authService.authenticated();
	List<Movie> movie = Arrays.asList(movieRepository.getOne(movieId));
	List<Review> list = repository.find(user, movie);
	return list.stream().map(x -> new ReviewDTO(x)).collect(Collectors.toList());
	
	}
}
