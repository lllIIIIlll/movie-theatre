package net.ow.movie.theatre.fixture;

import java.util.List;
import net.ow.movie.tmdb.model.search.TMDBMovieSearchResult;

public class MockTMDBMovieSearchResult {
    public static TMDBMovieSearchResult mock(Integer id, String title, List<Integer> genreIds) {
        TMDBMovieSearchResult searchResult = new TMDBMovieSearchResult();

        searchResult.setId(id);
        searchResult.setTitle(title);
        searchResult.setGenreIds(genreIds);

        return searchResult;
    }
}
