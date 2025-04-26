package net.ow.movie.theatre.fixture;

import net.ow.movie.tmdb.model.tv.TMDBTVSeason;

public class MockTMDBTVSeason {
    public static TMDBTVSeason mock(Integer id, String name) {
        TMDBTVSeason tmdbtvSeason = new TMDBTVSeason();

        tmdbtvSeason.setId(id);
        tmdbtvSeason.setName(name);

        return tmdbtvSeason;
    }
}
