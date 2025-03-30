package net.ow.movie.theatre.service;

import java.util.List;
import java.util.Map;
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
                baseMovieDTOMapper.fromTMDBPaginatedBaseMovies(tmdbPaginatedResponse);
        if (null == paginatedResponse) {
            return new PaginatedResponse<>();
        }

        List<BaseMovieDTO> movies = paginatedResponse.getData();
        if (null == movies) {
            System.out.println(20);
            System.out.println(20);
            System.out.println(20);
            return new PaginatedResponse<>();
        }

        Map<Integer, GenreDTO> genreIdToGenreMap = genreService.getAllGenresAsMap(language);
        movies.forEach(movie -> movie.setGenres(genreIdToGenreMap));

        return paginatedResponse;
    }
}
