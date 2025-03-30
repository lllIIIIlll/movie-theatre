package net.ow.movie.theatre.fixture;

import net.ow.movie.tmdb.model.movie.TMDBBaseMovie;

public class MockTMDBBaseMovie {
    public static TMDBBaseMovie mock(Integer id) {
        TMDBBaseMovie movie = new TMDBBaseMovie();

        movie.setId(id);

        return movie;
    }
}
