package net.ow.movie.theatre.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.mapper.PaginatedResponseMapper;
import net.ow.movie.theatre.mapper.movie.BaseMovieDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.movie.TMDBBaseMovie;
import net.ow.movie.tmdb.model.trending.TMDBTrendingMovie;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {
    private final TMDBFeignClient tmdbFeignClient;

    private final PaginatedResponseMapper paginatedResponseMapper;

    private final BaseMovieDTOMapper baseMovieDTOMapper;

    private final GenreService genreService;

    public PaginatedResponse<BaseMovieDTO> getTrendingMovies(
            String timeWindow, String language, Integer page) {
        log.debug("Fetching trending movies from tmdb");
        TMDBPaginatedResponse<TMDBTrendingMovie> tmdbPaginatedResponse =
                tmdbFeignClient.getTrendingMovies(timeWindow, language, page);
        log.debug("Fetched trending movies from tmdb");

        PaginatedResponse<BaseMovieDTO> paginatedResponse =
                baseMovieDTOMapper.fromTMDBPaginatedTrendingMovies(tmdbPaginatedResponse);

        // NOTE: When fetching trending movies from TMDB,
        // only ids are included in the response for genres.
        Map<Integer, GenreDTO> genreIdToGenreMap = genreService.getMovieGenresAsMap(language);
        paginatedResponse
                .getData()
                .forEach(movie -> enrichBaseMovieWithGenres(movie, genreIdToGenreMap));

        return paginatedResponse;
    }

    public PaginatedResponse<BaseMovieDTO> getNowPlayingMovies(
            String language, Integer page, String region) {
        log.debug("Fetching now playing movies from tmdb");
        TMDBPaginatedResponse<TMDBBaseMovie> tmdbPaginatedResponse =
                tmdbFeignClient.getNowPlayingMovies(language, page, region);
        log.debug("Fetched now playing movies from tmdb");

        PaginatedResponse<BaseMovieDTO> paginatedResponse =
                baseMovieDTOMapper.fromTMDBPaginatedBaseMovies(tmdbPaginatedResponse);

        // NOTE: When fetching now playing movies from TMDB,
        // only ids are included in the response for genres.
        Map<Integer, GenreDTO> genreIdToGenreMap = genreService.getMovieGenresAsMap(language);
        paginatedResponse
                .getData()
                .forEach(movie -> enrichBaseMovieWithGenres(movie, genreIdToGenreMap));

        return paginatedResponse;
    }

    public PaginatedResponse<BaseMovieDTO> getPopularMovies(
            String language, Integer page, String region) {
        log.debug("Fetching popular movies from tmdb");
        TMDBPaginatedResponse<TMDBBaseMovie> tmdbPaginatedResponse =
                tmdbFeignClient.getPopularMovies(language, page, region);
        log.debug("Fetched popular movies from tmdb");

        PaginatedResponse<BaseMovieDTO> paginatedResponse =
                baseMovieDTOMapper.fromTMDBPaginatedBaseMovies(tmdbPaginatedResponse);

        // NOTE: When fetching popular movies from TMDB,
        // only ids are included in the response for genres.
        Map<Integer, GenreDTO> genreIdToGenreMap = genreService.getMovieGenresAsMap(language);
        paginatedResponse
                .getData()
                .forEach(movie -> enrichBaseMovieWithGenres(movie, genreIdToGenreMap));

        return paginatedResponse;
    }

    private void enrichBaseMovieWithGenres(
            BaseMovieDTO baseMovie, Map<Integer, GenreDTO> movieGenresMap) {
        log.debug("Enriching base movie with genre details.");

        List<Integer> genreIds = baseMovie.getGenres().stream().map(GenreDTO::getId).toList();
        List<GenreDTO> genres = genreIds.stream().map(movieGenresMap::get).toList();
        baseMovie.setGenres(genres);
    }
}
