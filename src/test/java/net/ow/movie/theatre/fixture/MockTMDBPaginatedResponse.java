package net.ow.movie.theatre.fixture;

import java.util.List;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.movie.TMDBBaseMovie;
import net.ow.movie.tmdb.model.search.TMDBSearchResult;

public class MockTMDBPaginatedResponse {
    public static TMDBPaginatedResponse<TMDBBaseMovie> mockTMDBPaginatedBaseMovie(
            List<TMDBBaseMovie> results) {
        TMDBPaginatedResponse<TMDBBaseMovie> tmdbPaginatedResponse = new TMDBPaginatedResponse<>();

        tmdbPaginatedResponse.setResults(results);
        tmdbPaginatedResponse.setPage(1);
        tmdbPaginatedResponse.setTotalPages(1);
        tmdbPaginatedResponse.setTotalResults(null == results ? 0 : results.size());

        return tmdbPaginatedResponse;
    }

    public static TMDBPaginatedResponse<TMDBSearchResult> mockTMDBPaginatedSearchResult(
            List<TMDBSearchResult> results) {
        TMDBPaginatedResponse<TMDBSearchResult> tmdbPaginatedResponse = new TMDBPaginatedResponse<>();

        tmdbPaginatedResponse.setResults(results);
        tmdbPaginatedResponse.setPage(1);
        tmdbPaginatedResponse.setTotalPages(1);
        tmdbPaginatedResponse.setTotalResults(null == results ? 0 : results.size());

        return tmdbPaginatedResponse;
    }
}
