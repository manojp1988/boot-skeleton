package com.example.boot.service;

import com.example.boot.data.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);

    static List<Movie> movies = Arrays.asList(
            new Movie("Test 1", Calendar.getInstance().getTime()),
            new Movie("Test 2", Calendar.getInstance().getTime())
    );


    @Cacheable(value = "movies")
    public List<Movie> getMovies() {
        log.info("Entered getMovies()...");
        return movies;
    }
}
