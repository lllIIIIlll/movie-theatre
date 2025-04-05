package net.ow.movie.theatre.fixture;

import java.util.List;
import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.dto.trending.TrendingDTO;
import net.ow.movie.theatre.dto.tv.BaseTVShowDTO;

public class MockPaginatedResponse {
    public static PaginatedResponse<TrendingDTO> mockPaginatedTrending(List<TrendingDTO> data) {
        PaginatedResponse<TrendingDTO> paginatedResponse = new PaginatedResponse<>();

        paginatedResponse.setData(data);
        paginatedResponse.setPage(1);
        paginatedResponse.setTotalPages(1);
        paginatedResponse.setTotal(null == data ? 0 : data.size());

        return paginatedResponse;
    }

    public static PaginatedResponse<BaseTVShowDTO> mockPaginatedBaseTVShowDTO(
            List<BaseTVShowDTO> data) {
        PaginatedResponse<BaseTVShowDTO> paginatedResponse = new PaginatedResponse<>();

        paginatedResponse.setData(data);
        paginatedResponse.setPage(1);
        paginatedResponse.setTotalPages(1);
        paginatedResponse.setTotal(null == data ? 0 : data.size());

        return paginatedResponse;
    }

    public static PaginatedResponse<BaseMovieDTO> mockPaginatedBaseMovieDTO(
            List<BaseMovieDTO> data) {
        PaginatedResponse<BaseMovieDTO> paginatedResponse = new PaginatedResponse<>();

        paginatedResponse.setData(data);
        paginatedResponse.setPage(1);
        paginatedResponse.setTotalPages(1);
        paginatedResponse.setTotal(null == data ? 0 : data.size());

        return paginatedResponse;
    }

    public static PaginatedResponse<SearchResultDTO> mockPaginatedSearchResultDTO(
            List<SearchResultDTO> data) {
        PaginatedResponse<SearchResultDTO> paginatedResponse = new PaginatedResponse<>();

        paginatedResponse.setData(data);
        paginatedResponse.setPage(1);
        paginatedResponse.setTotalPages(1);
        paginatedResponse.setTotal(null == data ? 0 : data.size());

        return paginatedResponse;
    }
}
