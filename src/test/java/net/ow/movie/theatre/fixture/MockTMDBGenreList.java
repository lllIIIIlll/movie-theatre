package net.ow.movie.theatre.fixture;

import java.util.List;
import net.ow.movie.tmdb.model.genre.TMDBGenre;
import net.ow.movie.tmdb.model.genre.TMDBGenreList;

public class MockTMDBGenreList {
    public static TMDBGenreList mock() {
        return new TMDBGenreList();
    }

    public static TMDBGenreList mock(List<TMDBGenre> genres) {
        TMDBGenreList tmdbGenreList = new TMDBGenreList();

        tmdbGenreList.setGenres(genres);

        return tmdbGenreList;
    }
}
