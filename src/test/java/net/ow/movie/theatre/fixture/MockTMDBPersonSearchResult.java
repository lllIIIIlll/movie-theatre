package net.ow.movie.theatre.fixture;

import net.ow.movie.tmdb.model.search.TMDBPersonSearchResult;

public class MockTMDBPersonSearchResult {
    public static TMDBPersonSearchResult mock(Integer id, String name) {
        TMDBPersonSearchResult searchResult = new TMDBPersonSearchResult();

        searchResult.setId(id);
        searchResult.setName(name);

        return searchResult;
    }
}
