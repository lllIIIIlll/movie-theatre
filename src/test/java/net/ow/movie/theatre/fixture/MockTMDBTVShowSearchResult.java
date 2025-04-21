package net.ow.movie.theatre.fixture;

import java.util.List;
import net.ow.movie.tmdb.model.search.TMDBTVShowSearchResult;

public class MockTMDBTVShowSearchResult {
    public static TMDBTVShowSearchResult mock(Integer id, String name, List<Integer> genreIds) {
        TMDBTVShowSearchResult searchResult = new TMDBTVShowSearchResult();

        searchResult.setId(id);
        searchResult.setName(name);
        searchResult.setGenreIds(genreIds);

        return searchResult;
    }
}
