package net.ow.movie.theatre.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.mapper.PaginatedResponseMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.movie.TMDBBaseMovie;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {
    private final TMDBFeignClient tmdbFeignClient;

    private final PaginatedResponseMapper paginatedResponseMapper;

    private final GenreService genreService;

    public PaginatedResponse<BaseMovieDTO> getNowPlayingMovies(
            String language, Integer page, String region) {
        log.debug("Fetching now playing movies from tmdb");
        TMDBPaginatedResponse<TMDBBaseMovie> tmdbPaginatedResponse =
                tmdbFeignClient.getNowPlayingMovies(language, page, region);
        log.debug("Fetched now playing movies from tmdb");

        PaginatedResponse<BaseMovieDTO> paginatedResponse =
                paginatedResponseMapper.fromTMDBPaginatedBaseMovies(tmdbPaginatedResponse);

        return enrichMoviesWithGenreDetails(paginatedResponse, language);
    }

    public PaginatedResponse<BaseMovieDTO> getPopularMovies(
            String language, Integer page, String region) {
        log.debug("Fetching popular movies from tmdb");
        TMDBPaginatedResponse<TMDBBaseMovie> tmdbPaginatedResponse =
                tmdbFeignClient.getPopularMovies(language, page, region);
        log.debug("Fetched popular movies from tmdb");

        PaginatedResponse<BaseMovieDTO> paginatedResponse =
                paginatedResponseMapper.fromTMDBPaginatedBaseMovies(tmdbPaginatedResponse);

        return enrichMoviesWithGenreDetails(paginatedResponse, language);
    }

    private PaginatedResponse<BaseMovieDTO> enrichMoviesWithGenreDetails(
            PaginatedResponse<BaseMovieDTO> paginatedResponse, String language) {
        // NOTE: When fetching popular movies from TMDB,
        // only ids are included in the response for genres.
        Map<Integer, GenreDTO> genreIdToGenreMap = genreService.getAllGenresAsMap(language);
        paginatedResponse
                .getData()
                .forEach(
                        movie -> {
                            List<Integer> genreIds =
                                    movie.getGenres().stream().map(GenreDTO::getId).toList();
                            List<GenreDTO> genres =
                                    genreIds.stream().map(genreIdToGenreMap::get).toList();
                            movie.setGenres(genres);
                        });

        return paginatedResponse;
    }
}
