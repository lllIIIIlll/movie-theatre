package net.ow.movie.theatre.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.mapper.movie.BaseMovieDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.movie.TMDBBaseMovie;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {
    private final TMDBFeignClient tmdbFeignClient;

    private final BaseMovieDTOMapper baseMovieDTOMapper;

    private final GenreService genreService;

    public PaginatedResponse<BaseMovieDTO> getPopularMovies(
            String language, Integer page, String region) {
        log.debug("Fetching popular movie from tmdb");
        TMDBPaginatedResponse<TMDBBaseMovie> tmdbPaginatedResponse =
                tmdbFeignClient.getPopularMovies(language, page, region);
        log.debug("Fetched popular movie from tmdb");

        PaginatedResponse<BaseMovieDTO> paginatedResponse =
                baseMovieDTOMapper.from(tmdbPaginatedResponse);
        if (null == paginatedResponse || null == paginatedResponse.getData()) {
            return paginatedResponse;
        }

        Map<Integer, GenreDTO> genreIdToGenreMap = genreService.getAllGenresAsMap(language);
        Map<Integer, List<Integer>> movieIdToGenreIdsMap =
                createMovieToGenreIdsMapping(tmdbPaginatedResponse.getResults());

        paginatedResponse
                .getData()
                .forEach(
                        movie -> {
                            Integer movieId = movie.getId();
                            List<Integer> genreIds =
                                    movieIdToGenreIdsMap.getOrDefault(
                                            movieId, Collections.emptyList());
                            movie.setGenres(
                                    genreService.findGenresByIds(genreIds, genreIdToGenreMap));
                        });

        return paginatedResponse;
    }

    private Map<Integer, List<Integer>> createMovieToGenreIdsMapping(List<TMDBBaseMovie> movies) {
        return movies.stream()
                .collect(Collectors.toMap(TMDBBaseMovie::getId, TMDBBaseMovie::getGenreIds));
    }
}
