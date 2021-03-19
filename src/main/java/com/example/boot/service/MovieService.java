package com.example.boot.service;

import com.example.boot.data.entity.Movie;
import com.example.boot.data.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);
    private MovieRepository movieRepository;

    @Autowired
    MovieService(MovieRepository movieRepository){

        this.movieRepository = movieRepository;
    }

    @Cacheable(value = "movies")
    public List<Movie> getMovies() {
        log.info("Entered getMovies()...");
        return movieRepository.findAll();
    }
}
