package net.ow.movie.theatre.fixture;

import net.ow.movie.tmdb.model.trending.TMDBTrendingPerson;

public class MockTMDBTrendingPerson {
    public static TMDBTrendingPerson mock(Integer id) {
        TMDBTrendingPerson trendingPerson = new TMDBTrendingPerson();

        trendingPerson.setId(id);

        return trendingPerson;
    }
}
