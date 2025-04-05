package net.ow.movie.theatre.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.trending.TrendingTVShowDTO;
import net.ow.movie.theatre.fixture.*;
import net.ow.movie.theatre.mapper.PaginatedResponseMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.trending.TMDBTrendingTVShow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TVShowServiceTest {
    @InjectMocks private TVShowService tvShowService;

    @Mock private TMDBFeignClient tmdbFeignClient;

    @Mock private PaginatedResponseMapper paginatedResponseMapper;

    @Mock private GenreService genreService;

    @Test
    void getTrendingTVShowsTest_OK() {
        String timeWindow = "day";
        String language = "zh-CN";
        Integer page = 1;

        Integer genreId = 1;
        String genreName = "genre-name";
        GenreDTO genre = MockGenreDTO.mock(genreId, genreName);
        Map<Integer, GenreDTO> genreIdToGenreMap = Collections.singletonMap(genreId, genre);

        Integer tvShowId = 1;
        String tvShowName = "tv-show-name";
        TrendingTVShowDTO tvShow =
                MockTrendingTVShowDTO.mock(
                        tvShowId, tvShowName, List.of(MockGenreDTO.mock(genreId, null)));
        PaginatedResponse<TrendingTVShowDTO> paginatedResponse =
                MockPaginatedResponse.mockPaginatedTrendingTVShow(List.of(tvShow));

        TMDBTrendingTVShow trendingTVShow = MockTMDBTrendingTVShow.mock(tvShowId);
        TMDBPaginatedResponse<TMDBTrendingTVShow> tmdbPaginatedResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedTrendingTVShow(List.of(trendingTVShow));

        when(tmdbFeignClient.getTrendingTVShows(timeWindow, language, page))
                .thenReturn(tmdbPaginatedResponse);
        when(paginatedResponseMapper.fromTMDBPaginatedTrendingTVShows(tmdbPaginatedResponse))
                .thenReturn(paginatedResponse);

        when(genreService.getAllGenresAsMap(language)).thenReturn(genreIdToGenreMap);

        PaginatedResponse<TrendingTVShowDTO> actualResponse =
                tvShowService.getTrendingTVShows(timeWindow, page, language);

        assertNotNull(actualResponse);

        assertFalse(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(genre, tvShow.getGenres().get(0));
    }

    @Test
    void getTrendingTVShowsTest_whenNullTMDBResponse_thenReturnsEmptyPaginatedResponse() {
        String timeWindow = "day";
        String language = "zh-CN";
        Integer page = 1;

        PaginatedResponse<TrendingTVShowDTO> expectedPaginatedResponse =
                MockPaginatedResponse.mockPaginatedTrendingTVShow(Collections.emptyList());

        when(tmdbFeignClient.getTrendingTVShows(timeWindow, language, page)).thenReturn(null);
        when(paginatedResponseMapper.fromTMDBPaginatedTrendingTVShows(null))
                .thenReturn(expectedPaginatedResponse);

        PaginatedResponse<TrendingTVShowDTO> actualResponse =
                tvShowService.getTrendingTVShows(timeWindow, page, language);

        assertTrue(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(1, actualResponse.getTotalPages());
        assertEquals(0, actualResponse.getTotal());
    }

    @Test
    void getTrendingTVShowsTest_whenTMDBEmptyData_thenReturnsEmptyPaginatedResponse() {
        String timeWindow = "day";
        String language = "zh-CN";
        Integer page = 1;

        TMDBPaginatedResponse<TMDBTrendingTVShow> tmdbPaginatedResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedTrendingTVShow(Collections.emptyList());

        PaginatedResponse<TrendingTVShowDTO> expectedPaginatedResponse =
                MockPaginatedResponse.mockPaginatedTrendingTVShow(Collections.emptyList());

        when(tmdbFeignClient.getTrendingTVShows(timeWindow, language, page))
                .thenReturn(tmdbPaginatedResponse);
        when(paginatedResponseMapper.fromTMDBPaginatedTrendingTVShows(tmdbPaginatedResponse))
                .thenReturn(expectedPaginatedResponse);

        PaginatedResponse<TrendingTVShowDTO> actualResponse =
                tvShowService.getTrendingTVShows(timeWindow, page, language);

        assertTrue(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(1, actualResponse.getTotalPages());
        assertEquals(0, actualResponse.getTotal());
    }
}
