package net.ow.movie.theatre.fixture;

import java.util.List;
import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.dto.trending.TrendingMovieDTO;
import net.ow.movie.theatre.dto.trending.TrendingTVShowDTO;

public class MockPaginatedResponse {
    public static PaginatedResponse<TrendingTVShowDTO> mockPaginatedTrendingTVShow(
            List<TrendingTVShowDTO> data) {
        PaginatedResponse<TrendingTVShowDTO> paginatedResponse = new PaginatedResponse<>();

        paginatedResponse.setData(data);
        paginatedResponse.setPage(1);
        paginatedResponse.setTotalPages(1);
        paginatedResponse.setTotal(null == data ? 0 : data.size());

        return paginatedResponse;
    }

    public static PaginatedResponse<TrendingMovieDTO> mockPaginatedTrendingMovie(
            List<TrendingMovieDTO> data) {
        PaginatedResponse<TrendingMovieDTO> paginatedResponse = new PaginatedResponse<>();

        paginatedResponse.setData(data);
        paginatedResponse.setPage(1);
        paginatedResponse.setTotalPages(1);
        paginatedResponse.setTotal(null == data ? 0 : data.size());

        return paginatedResponse;
    }

    public static PaginatedResponse<BaseMovieDTO> mockPaginatedBaseMovie(List<BaseMovieDTO> data) {
        PaginatedResponse<BaseMovieDTO> paginatedResponse = new PaginatedResponse<>();

        paginatedResponse.setData(data);
        paginatedResponse.setPage(1);
        paginatedResponse.setTotalPages(1);
        paginatedResponse.setTotal(null == data ? 0 : data.size());

        return paginatedResponse;
    }

    public static PaginatedResponse<SearchResultDTO> mockPaginatedSearchResult(
            List<SearchResultDTO> data) {
        PaginatedResponse<SearchResultDTO> paginatedResponse = new PaginatedResponse<>();

        paginatedResponse.setData(data);
        paginatedResponse.setPage(1);
        paginatedResponse.setTotalPages(1);
        paginatedResponse.setTotal(null == data ? 0 : data.size());

        return paginatedResponse;
    }
}
