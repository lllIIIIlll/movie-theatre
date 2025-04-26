package net.ow.movie.theatre.fixture;

import net.ow.movie.tmdb.model.tv.TMDBTVShow;

public class MockTMDBTVShow {
    public static TMDBTVShow mock(Integer id) {
        TMDBTVShow tmdbtvShow = new TMDBTVShow();

        tmdbtvShow.setId(id);

        return tmdbtvShow;
    }
}
