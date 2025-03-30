package net.ow.movie.theatre.fixture;

import net.ow.movie.tmdb.model.genre.TMDBGenre;

public class MockTMDBGenre {
    public static TMDBGenre mock(Integer id) {
        TMDBGenre tmdbGenre = new TMDBGenre();

        tmdbGenre.setId(id);

        return tmdbGenre;
    }

    public static TMDBGenre mock(Integer id, String name) {
        TMDBGenre tmdbGenre = new TMDBGenre();

        tmdbGenre.setId(id);
        tmdbGenre.setName(name);

        return tmdbGenre;
    }
}
