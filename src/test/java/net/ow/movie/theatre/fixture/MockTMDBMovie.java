package net.ow.movie.theatre.fixture;

import net.ow.movie.tmdb.model.movie.TMDBMovie;

public class MockTMDBMovie {
    public static TMDBMovie mock(Integer id) {
        TMDBMovie movie = new TMDBMovie();

        movie.setId(id);

        return movie;
    }
}
