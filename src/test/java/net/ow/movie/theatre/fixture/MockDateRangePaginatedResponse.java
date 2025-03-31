package net.ow.movie.theatre.fixture;

import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.tmdb.model.common.TMDBDateRangePaginatedResponse;
import net.ow.movie.tmdb.model.movie.TMDBBaseMovie;

import java.util.List;

public class MockDateRangePaginatedResponse {
    public static TMDBDateRangePaginatedResponse<TMDBBaseMovie> mockDateRangePaginatedTMDBBaseMovie(List<TMDBBaseMovie> results) {
        TMDBDateRangePaginatedResponse<TMDBBaseMovie> paginatedResponse = new TMDBDateRangePaginatedResponse<>();

        paginatedResponse.setResults(results);
        paginatedResponse.setPage(1);
        paginatedResponse.setTotalPages(1);
        paginatedResponse.setTotalResults(null == results ? 0 : results.size());

        return paginatedResponse;
    }
}
