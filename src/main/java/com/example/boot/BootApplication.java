package com.example.boot;

import com.example.boot.config.security.PasswordUtil;
import com.example.boot.data.entity.Movie;
import com.example.boot.data.entity.UserData;
import com.example.boot.data.repository.MovieRepository;
import com.example.boot.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;

@SpringBootApplication
@EnableCaching
public class BootApplication extends SpringBootServletInitializer implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }


    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        UserData user = new UserData();
        user.setName("Jeeva");
        user.setPassword(PasswordUtil.encrypt("password"));
        user.setEmail("test");

        userRepository.save(user);


        final Movie movie = new Movie();
        movie.setName("Hitman");
        movie.setReleaseDate(Calendar.getInstance().getTime());

        movieRepository.saveAndFlush(movie);

        final Movie movie1 = movieRepository.findByName("Hitman")
                                            .orElseThrow(EntityNotFoundException::new);

        movie1.setName("Batman");
        movie1.setDirector("Nolan");
        movieRepository.save(movie1);
    }
}
