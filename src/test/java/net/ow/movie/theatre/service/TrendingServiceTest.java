package net.ow.movie.theatre.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.trending.TrendingDTO;
import net.ow.movie.theatre.dto.trending.TrendingMovieDTO;
import net.ow.movie.theatre.dto.trending.TrendingPersonDTO;
import net.ow.movie.theatre.dto.trending.TrendingTVShowDTO;
import net.ow.movie.theatre.fixture.*;
import net.ow.movie.theatre.mapper.trending.TrendingDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.trending.TMDBTrending;
import net.ow.movie.tmdb.model.trending.TMDBTrendingMovie;
import net.ow.movie.tmdb.model.trending.TMDBTrendingPerson;
import net.ow.movie.tmdb.model.trending.TMDBTrendingTVShow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrendingServiceTest {
    @InjectMocks private TrendingService trendingService;

    @Mock private TMDBFeignClient tmdbFeignClient;

    @Mock private GenreService genreService;

    @Mock private TrendingDTOMapper trendingDTOMapper;

    @Test
    void getTrendingContentTest_OK() {
        String timeWindow = "day";
        String language = "zh-CN";
        Integer page = 1;

        Integer tvShowId = 1;
        String tvShowName = "tv-show-name";

        Integer movieId = 1;
        String movieName = "movie-name";

        Integer personId = 1;

        Integer genreId = 1;
        String genreName = "genre-name";

        GenreDTO genre = MockGenreDTO.mock(genreId, genreName);
        Map<Integer, GenreDTO> genreIdToGenreMap = Collections.singletonMap(genreId, genre);

        TrendingMovieDTO movie =
                MockTrendingMovieDTO.mock(
                        movieId, movieName, List.of(MockGenreDTO.mock(genreId, null)));
        TrendingTVShowDTO tvShow =
                MockTrendingTVShowDTO.mock(
                        tvShowId, tvShowName, List.of(MockGenreDTO.mock(genreId, null)));
        TrendingPersonDTO person = MockTrendingPersonDTO.mock(personId);

        PaginatedResponse<TrendingDTO> paginatedResponse =
                MockPaginatedResponse.mockPaginatedTrending(List.of(movie, tvShow, person));

        TMDBTrendingMovie tmdbTrendingMovie = MockTMDBTrendingMovie.mock(movieId);
        TMDBTrendingTVShow tmdbTrendingTVShow = MockTMDBTrendingTVShow.mock(tvShowId);
        TMDBTrendingPerson trendingPerson = MockTMDBTrendingPerson.mock(personId);

        TMDBPaginatedResponse<TMDBTrending> tmdbPaginatedResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedTrending(
                        List.of(tmdbTrendingMovie, tmdbTrendingTVShow, trendingPerson));

        when(tmdbFeignClient.getTrending(timeWindow, language, page))
                .thenReturn(tmdbPaginatedResponse);
        when(trendingDTOMapper.fromTMDBPaginatedTrending(tmdbPaginatedResponse))
                .thenReturn(paginatedResponse);

        when(genreService.getMovieGenresAsMap(language)).thenReturn(genreIdToGenreMap);
        when(genreService.getTVShowGenresAsMap(language)).thenReturn(genreIdToGenreMap);

        PaginatedResponse<TrendingDTO> actualResponse =
                trendingService.getTrendingContent(timeWindow, page, language);

        assertNotNull(actualResponse);

        assertFalse(actualResponse.getData().isEmpty());
        assertEquals(3, actualResponse.getTotal());
        assertEquals(1, actualResponse.getPage());
        assertEquals(genre, movie.getGenres().get(0));
        assertEquals(genre, tvShow.getGenres().get(0));
    }

    @Test
    void getTrendingContentTest_whenNullTMDBResponse_thenReturnsEmptyPaginatedResponse() {
        String timeWindow = "day";
        String language = "zh-CN";
        Integer page = 1;

        PaginatedResponse<TrendingDTO> expectedPaginatedResponse =
                MockPaginatedResponse.mockPaginatedTrending(Collections.emptyList());

        when(tmdbFeignClient.getTrending(timeWindow, language, page)).thenReturn(null);
        when(trendingDTOMapper.fromTMDBPaginatedTrending(null))
                .thenReturn(expectedPaginatedResponse);

        PaginatedResponse<TrendingDTO> actualResponse =
                trendingService.getTrendingContent(timeWindow, page, language);

        assertTrue(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(1, actualResponse.getTotalPages());
        assertEquals(0, actualResponse.getTotal());
    }

    @Test
    void getTrendingContentTest_whenTMDBEmptyData_thenReturnsEmptyPaginatedResponse() {
        String timeWindow = "day";
        String language = "zh-CN";
        Integer page = 1;

        TMDBPaginatedResponse<TMDBTrending> tmdbPaginatedResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedTrending(Collections.emptyList());

        PaginatedResponse<TrendingDTO> expectedPaginatedResponse =
                MockPaginatedResponse.mockPaginatedTrending(Collections.emptyList());

        when(tmdbFeignClient.getTrending(timeWindow, language, page))
                .thenReturn(tmdbPaginatedResponse);
        when(trendingDTOMapper.fromTMDBPaginatedTrending(tmdbPaginatedResponse))
                .thenReturn(expectedPaginatedResponse);

        PaginatedResponse<TrendingDTO> actualResponse =
                trendingService.getTrendingContent(timeWindow, page, language);

        assertTrue(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(1, actualResponse.getTotalPages());
        assertEquals(0, actualResponse.getTotal());
    }
}
