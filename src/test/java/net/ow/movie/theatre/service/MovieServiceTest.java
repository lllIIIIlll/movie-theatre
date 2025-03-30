package net.ow.movie.theatre.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.mapper.movie.BaseMovieDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.movie.TMDBBaseMovie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @InjectMocks private MovieService movieService;

    @Mock private TMDBFeignClient tmdbFeignClient;

    @Mock private BaseMovieDTOMapper baseMovieDTOMapper;

    @Mock private GenreService genreService;

    @Mock private TMDBPaginatedResponse<TMDBBaseMovie> tmdbPaginatedResponse;

    @Mock private PaginatedResponse<BaseMovieDTO> paginatedResponse;

    @Mock private TMDBBaseMovie tmdbMovie1;

    @Spy private BaseMovieDTO movie1;

    @Mock private GenreDTO genre;

    @Test
    void getPopularMoviesTest_OK() {
        String language = "zh-CN";
        Integer page = 1;
        String region = "CH";

        Integer movieId = 1;
        Integer genreId = 1;

        Map<Integer, GenreDTO> genreIdToGenreMap = Collections.singletonMap(genreId, genre);
        List<GenreDTO> genres = List.of(genre);

        List<Integer> genreIds = Collections.singletonList(genreId);
        when(tmdbMovie1.getId()).thenReturn(movieId);
        when(tmdbMovie1.getGenreIds()).thenReturn(genreIds);

        List<TMDBBaseMovie> tmdbMovies = List.of(tmdbMovie1);
        when(tmdbPaginatedResponse.getResults()).thenReturn(tmdbMovies);

        when(movie1.getId()).thenReturn(movieId);

        List<BaseMovieDTO> movies = Collections.singletonList(movie1);
        when(paginatedResponse.getData()).thenReturn(movies);

        when(tmdbFeignClient.getPopularMovies(language, page, region))
                .thenReturn(tmdbPaginatedResponse);
        when(baseMovieDTOMapper.fromTMDBPaginatedBaseMovies(tmdbPaginatedResponse))
                .thenReturn(paginatedResponse);
        when(genreService.getAllGenresAsMap(language)).thenReturn(genreIdToGenreMap);
        when(genreService.findGenresByIds(genreIds, genreIdToGenreMap)).thenReturn(genres);

        PaginatedResponse<BaseMovieDTO> actualResponse =
                movieService.getPopularMovies(language, page, region);

        assertNotNull(actualResponse);
        assertFalse(actualResponse.getData().isEmpty());

        verify(movie1, times(1)).setGenres(genres);
        assertEquals(1, actualResponse.getData().get(0).getGenres().size());
    }

    @Test
    void getPopularMoviesTest_whenNullTMDBResponse_thenReturnsNull() {
        String language = "zh-CN";
        Integer page = 1;
        String region = "CH";

        when(tmdbFeignClient.getPopularMovies(language, page, region)).thenReturn(null);
        when(baseMovieDTOMapper.fromTMDBPaginatedBaseMovies(null)).thenReturn(null);

        PaginatedResponse<BaseMovieDTO> actualResponse =
                movieService.getPopularMovies(language, page, region);

        assertNull(actualResponse);
    }

    @Test
    void getPopularMoviesTest_whenTMDBEmptyData_thenReturnsEmptyPaginatedResponse() {
        String language = "zh-CN";
        Integer page = 1;
        String region = "CH";

        when(tmdbPaginatedResponse.getResults()).thenReturn(Collections.emptyList());
        when(paginatedResponse.getData()).thenReturn(Collections.emptyList());

        when(tmdbFeignClient.getPopularMovies(language, page, region))
                .thenReturn(tmdbPaginatedResponse);
        when(baseMovieDTOMapper.fromTMDBPaginatedBaseMovies(tmdbPaginatedResponse))
                .thenReturn(paginatedResponse);

        PaginatedResponse<BaseMovieDTO> actualResponse =
                movieService.getPopularMovies(language, page, region);

        assertNotNull(actualResponse);
        assertTrue(actualResponse.getData().isEmpty());
    }
}
