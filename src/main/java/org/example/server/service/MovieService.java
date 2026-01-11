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

    public List<Movie> searchByTitle(String search) {
        return movieDAO.searchByTitle(search);
    }

    public List<Movie> findFiltered(String search, String genre) {
        return movieDAO.findFiltered(search, genre);
    }
    public List<String> getAllGenres() {
        return movieDAO.getAllGenres();
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
