package net.ow.movie.theatre.fixture;

import net.ow.movie.tmdb.model.trending.TMDBTrendingMovie;

public class MockTMDBTrendingMovie {
    public static TMDBTrendingMovie mock(Integer id) {
        TMDBTrendingMovie trendingMovie = new TMDBTrendingMovie();

        trendingMovie.setId(id);

        return trendingMovie;
    }
}
