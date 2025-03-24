package net.ow.movie.theatre.fixture;

import net.ow.movie.tmdb.model.genre.TMDBGenre;

public class MockTMDBGenre {
    public static TMDBGenre mock(Integer id) {
        return mock(id, "Action");
    }

    public static TMDBGenre mock(Integer id, String name) {
        TMDBGenre tmdbGenre = new TMDBGenre();

        tmdbGenre.setId(id);
        tmdbGenre.setName(name);

        return tmdbGenre;
    }
}
