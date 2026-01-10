package org.example.server.service;

import common.Movie;
import org.example.server.dao.MovieDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieDAO movieDAO;

    public MovieService(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    public List<Movie> findAll() {
        return movieDAO.findAll();
    }

    public void addMovie(Movie movie) {
        movieDAO.insert(movie);
    }
    public void deleteMovie(int id) {
        movieDAO.deleteById(id);
    }
    public Movie findById(int id) {
        return movieDAO.findById(id);
    }

}
