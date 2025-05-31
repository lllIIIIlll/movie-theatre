package net.ow.movie.theatre.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.movie.DiscoverMovieRequest;
import net.ow.movie.theatre.dto.movie.MovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.fixture.*;
import net.ow.movie.theatre.mapper.movie.BaseMovieDTOMapper;
import net.ow.movie.theatre.mapper.movie.MovieDTOMapper;
import net.ow.movie.theatre.mapper.movie.TMDBDiscoverMovieRequestMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.discover.TMDBDiscoverMovieRequest;
import net.ow.movie.tmdb.model.movie.TMDBBaseMovie;
import net.ow.movie.tmdb.model.movie.TMDBMovie;
import net.ow.movie.tmdb.model.trending.TMDBTrendingMovie;
import net.ow.shared.errorutils.model.APIException;
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

    @Mock private MovieDTOMapper movieDTOMapper;

    @Mock private TMDBDiscoverMovieRequestMapper tmdbDiscoverMovieRequestMapper;

    @Mock private GenreService genreService;

    @Test
    void getTrendingMoviesTest_OK() {
        String timeWindow = "day";
        String language = "zh-CN";
        Integer page = 1;

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
                MockPaginatedResponse.mockPaginatedBaseMovieDTO(List.of(movie));

        TMDBTrendingMovie tmdbTrendingMovie = MockTMDBTrendingMovie.mock(movieId);
        TMDBPaginatedResponse<TMDBTrendingMovie> tmdbPaginatedResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedTrendingMovie(
                        List.of(tmdbTrendingMovie));

        when(tmdbFeignClient.getTrendingMovies(timeWindow, language, page))
                .thenReturn(tmdbPaginatedResponse);
        when(baseMovieDTOMapper.fromTMDBPaginatedTrendingMovies(tmdbPaginatedResponse))
                .thenReturn(paginatedResponse);

        when(genreService.getMovieGenresAsMap(language)).thenReturn(genreIdToGenreMap);

        PaginatedResponse<BaseMovieDTO> actualResponse =
                movieService.getTrendingMovies(timeWindow, language, page);

        assertNotNull(actualResponse);

        assertFalse(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(genre, movie.getGenres().get(0));
    }

    @Test
    void getTrendingMoviesTest_whenNullTMDBResponse_thenReturnsEmptyPaginatedResponse() {
        String timeWindow = "day";
        String language = "zh-CN";
        Integer page = 1;

        PaginatedResponse<BaseMovieDTO> expectedPaginatedResponse =
                MockPaginatedResponse.mockPaginatedBaseMovieDTO(Collections.emptyList());

        when(tmdbFeignClient.getTrendingMovies(timeWindow, language, page)).thenReturn(null);
        when(baseMovieDTOMapper.fromTMDBPaginatedTrendingMovies(null))
                .thenReturn(expectedPaginatedResponse);

        PaginatedResponse<BaseMovieDTO> actualResponse =
                movieService.getTrendingMovies(timeWindow, language, page);

        assertTrue(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(1, actualResponse.getTotalPages());
        assertEquals(0, actualResponse.getTotal());
    }

    @Test
    void getTrendingMoviesTest_whenTMDBEmptyData_thenReturnsEmptyPaginatedResponse() {
        String timeWindow = "day";
        String language = "zh-CN";
        Integer page = 1;

        TMDBPaginatedResponse<TMDBTrendingMovie> tmdbPaginatedResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedTrendingMovie(Collections.emptyList());

        PaginatedResponse<BaseMovieDTO> expectedPaginatedResponse =
                MockPaginatedResponse.mockPaginatedBaseMovieDTO(Collections.emptyList());

        when(tmdbFeignClient.getTrendingMovies(timeWindow, language, page))
                .thenReturn(tmdbPaginatedResponse);
        when(baseMovieDTOMapper.fromTMDBPaginatedTrendingMovies(tmdbPaginatedResponse))
                .thenReturn(expectedPaginatedResponse);

        PaginatedResponse<BaseMovieDTO> actualResponse =
                movieService.getTrendingMovies(timeWindow, language, page);

        assertTrue(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(1, actualResponse.getTotalPages());
        assertEquals(0, actualResponse.getTotal());
    }

    @Test
    void getMovieDetailsTest_OK() {
        Integer movieId = 1;
        String language = "zh-CN";
        String movieName = "movie-name";

        Integer genreId = 1;
        String genreName = "genre-name";
        GenreDTO genre = MockGenreDTO.mock(genreId, genreName);
        Map<Integer, GenreDTO> genreIdToGenreMap = Collections.singletonMap(genreId, genre);

        Integer recommendedMovieId = 2;
        String recommendedMovieName = "recommended-movie-name";
        BaseMovieDTO recommendedMovie =
                MockBaseMovieDTO.mock(
                        recommendedMovieId,
                        recommendedMovieName,
                        List.of(MockGenreDTO.mock(genreId, null)));
        MovieDTO movieDTO = MockMovieDTO.mock(movieId, movieName, List.of(recommendedMovie));

        TMDBMovie tmdbMovie = MockTMDBMovie.mock(movieId);

        when(tmdbFeignClient.getMovieDetails(movieId, "credits,recommendations", language))
                .thenReturn(tmdbMovie);
        when(movieDTOMapper.fromTMDBMovie(tmdbMovie)).thenReturn(movieDTO);
        when(genreService.getMovieGenresAsMap(language)).thenReturn(genreIdToGenreMap);

        MovieDTO actualMovieDTO = movieService.getMovieDetails(movieId, language);

        assertNotNull(actualMovieDTO);
        assertEquals(movieDTO.getName(), actualMovieDTO.getName());
        assertEquals(genre, actualMovieDTO.getRecommendations().get(0).getGenres().get(0));
    }

    @Test
    void getMovieDetailsTest_WhenTMDBThrowAPIException_thenThrowsAPIException() {
        Integer movieId = 1;
        String language = "zh-CN";
        when(tmdbFeignClient.getMovieDetails(movieId, "credits,recommendations", language))
                .thenThrow(APIException.class);

        assertThrows(APIException.class, () -> movieService.getMovieDetails(movieId, language));
    }

    @Test
    void discoverMoviesTest_OK() {
        Integer page = 1;
        String language = "en-US";
        Integer primaryReleaseYear = 2023;
        Instant primaryReleaseDateLessThen = Instant.now();

        Integer movieId = 1;
        String movieName = "movie-name";

        Integer genreId = 1;
        String genreName = "genre-name";

        DiscoverMovieRequest request =
                MockDiscoverMovieRequest.mock(primaryReleaseYear, primaryReleaseDateLessThen);
        TMDBDiscoverMovieRequest tmdbRequest =
                MockTMDBDiscoverMovieRequest.mock(
                        primaryReleaseYear, primaryReleaseDateLessThen, page, language);

        TMDBBaseMovie tmdbBaseMovie = MockTMDBBaseMovie.mock(1);
        TMDBPaginatedResponse<TMDBBaseMovie> tmdbResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedBaseMovie(List.of(tmdbBaseMovie));

        GenreDTO genre = MockGenreDTO.mock(genreId, genreName);
        Map<Integer, GenreDTO> genreIdToGenreMap = Collections.singletonMap(genreId, genre);

        BaseMovieDTO baseMovie =
                MockBaseMovieDTO.mock(movieId, movieName, List.of(MockGenreDTO.mock(genreId)));
        PaginatedResponse<BaseMovieDTO> serviceResponse =
                MockPaginatedResponse.mockPaginatedBaseMovieDTO(List.of(baseMovie));

        when(tmdbDiscoverMovieRequestMapper.fromDiscoverMovieRequest(request, language, page))
                .thenReturn(tmdbRequest);
        when(tmdbFeignClient.discoverMovies(tmdbRequest)).thenReturn(tmdbResponse);
        when(baseMovieDTOMapper.fromTMDBPaginatedBaseMovies(tmdbResponse))
                .thenReturn(serviceResponse);
        when(genreService.getMovieGenresAsMap(language)).thenReturn(genreIdToGenreMap);

        PaginatedResponse<BaseMovieDTO> actualResponse =
                movieService.discoverMovies(request, language, page);

        assertTrue(tmdbRequest.getIncludeAdult());
        assertFalse(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(genre, baseMovie.getGenres().get(0));
    }

    @Test
    void discoverMoviesTest_WhenNullTMDBResponse_thenReturnsEmptyPaginatedResponse() {
        Integer page = 1;
        String language = "en-US";
        Integer primaryReleaseYear = 2023;
        Instant primaryReleaseDateLessThen = Instant.now();

        DiscoverMovieRequest request =
                MockDiscoverMovieRequest.mock(primaryReleaseYear, primaryReleaseDateLessThen);
        TMDBDiscoverMovieRequest tmdbRequest =
                MockTMDBDiscoverMovieRequest.mock(
                        primaryReleaseYear, primaryReleaseDateLessThen, page, language);

        PaginatedResponse<BaseMovieDTO> serviceResponse =
                MockPaginatedResponse.mockPaginatedBaseMovieDTO(Collections.emptyList());

        when(tmdbDiscoverMovieRequestMapper.fromDiscoverMovieRequest(request, language, page))
                .thenReturn(tmdbRequest);
        when(tmdbFeignClient.discoverMovies(tmdbRequest)).thenReturn(null);
        when(baseMovieDTOMapper.fromTMDBPaginatedBaseMovies(null)).thenReturn(serviceResponse);
        when(genreService.getMovieGenresAsMap(language)).thenReturn(Collections.emptyMap());

        PaginatedResponse<BaseMovieDTO> actualResponse =
                movieService.discoverMovies(request, language, page);

        assertTrue(tmdbRequest.getIncludeAdult());
        assertTrue(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(1, actualResponse.getTotalPages());
        assertEquals(0, actualResponse.getTotal());
    }
}
