package net.ow.movie.theatre.fixture;

import net.ow.movie.tmdb.model.trending.TMDBTrendingTVShow;

public class MockTMDBTrendingTVShow {
    public static TMDBTrendingTVShow mock(Integer id) {
        TMDBTrendingTVShow trendingTVShow = new TMDBTrendingTVShow();

        trendingTVShow.setId(id);

        return trendingTVShow;
    }
}
