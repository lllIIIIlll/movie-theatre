package net.ow.movie.theatre.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.fixture.*;
import net.ow.movie.theatre.mapper.movie.BaseMovieDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.movie.TMDBBaseMovie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @InjectMocks private MovieService movieService;

    @Mock private TMDBFeignClient tmdbFeignClient;

    @Mock private BaseMovieDTOMapper baseMovieDTOMapper;

    @Mock private GenreService genreService;

    @Test
    void getPopularMoviesTest_OK() {
        String language = "zh-CN";
        Integer page = 1;
        String region = "CH";

        Integer genreId = 1;
        String genreName = "genre-name";
        GenreDTO genre = MockGenreDTO.mock(genreId, genreName);
        Map<Integer, GenreDTO> genreIdToGenreMap = Collections.singletonMap(genreId, genre);

        Integer movieId = 1;
        String movieName = "movie-name";
        BaseMovieDTO movie =
                MockBaseMovieDTO.mock(
                        movieId, movieName, List.of(MockGenreDTO.mock(genreId, null)));
        PaginatedResponse<BaseMovieDTO> paginatedResponse =
                MockPaginatedResponse.mockPaginatedBaseMovie(List.of(movie));

        TMDBBaseMovie tmdbBaseMovie = MockTMDBBaseMovie.mock(movieId);
        TMDBPaginatedResponse<TMDBBaseMovie> tmdbPaginatedResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedBaseMovie(List.of(tmdbBaseMovie));

        when(tmdbFeignClient.getPopularMovies(language, page, region))
                .thenReturn(tmdbPaginatedResponse);
        when(baseMovieDTOMapper.fromTMDBPaginatedBaseMovies(tmdbPaginatedResponse))
                .thenReturn(paginatedResponse);

        when(genreService.getAllGenresAsMap(language)).thenReturn(genreIdToGenreMap);

        PaginatedResponse<BaseMovieDTO> actualResponse =
                movieService.getPopularMovies(language, page, region);

        assertNotNull(actualResponse);

        assertFalse(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(genre, movie.getGenres().get(0));
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

        assertTrue(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(1, actualResponse.getTotalPages());
        assertEquals(0, actualResponse.getTotal());
    }

    @Test
    void getPopularMoviesTest_whenTMDBEmptyData_thenReturnsEmptyPaginatedResponse() {
        String language = "zh-CN";
        Integer page = 1;
        String region = "CH";

        PaginatedResponse<BaseMovieDTO> paginatedResponse =
                MockPaginatedResponse.mockPaginatedBaseMovie(Collections.emptyList());

        TMDBPaginatedResponse<TMDBBaseMovie> tmdbPaginatedResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedBaseMovie(Collections.emptyList());

        when(tmdbFeignClient.getPopularMovies(language, page, region))
                .thenReturn(tmdbPaginatedResponse);
        when(baseMovieDTOMapper.fromTMDBPaginatedBaseMovies(tmdbPaginatedResponse))
                .thenReturn(paginatedResponse);

        PaginatedResponse<BaseMovieDTO> actualResponse =
                movieService.getPopularMovies(language, page, region);

        assertNotNull(actualResponse);
        assertTrue(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(1, actualResponse.getTotalPages());
        assertEquals(0, actualResponse.getTotal());
    }

    @Test
    void getPopularMoviesTest_whenNullTMDBData_thenReturnsEmptyPaginatedResponse() {
        String language = "zh-CN";
        Integer page = 1;
        String region = "CH";

        PaginatedResponse<BaseMovieDTO> paginatedResponse =
                MockPaginatedResponse.mockPaginatedBaseMovie(null);

        TMDBPaginatedResponse<TMDBBaseMovie> tmdbPaginatedResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedBaseMovie(null);

        when(tmdbFeignClient.getPopularMovies(language, page, region))
                .thenReturn(tmdbPaginatedResponse);
        when(baseMovieDTOMapper.fromTMDBPaginatedBaseMovies(tmdbPaginatedResponse))
                .thenReturn(paginatedResponse);

        PaginatedResponse<BaseMovieDTO> actualResponse =
                movieService.getPopularMovies(language, page, region);

        assertNotNull(actualResponse);
        assertTrue(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(1, actualResponse.getTotalPages());
        assertEquals(0, actualResponse.getTotal());
    }
}
