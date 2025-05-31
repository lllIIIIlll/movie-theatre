package net.ow.movie.theatre.service;

import static net.ow.movie.theatre.constant.TMDBConstant.*;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ow.movie.theatre.constant.TMDBConstant;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.movie.DiscoverMovieRequest;
import net.ow.movie.theatre.dto.movie.MovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.mapper.movie.BaseMovieDTOMapper;
import net.ow.movie.theatre.mapper.movie.MovieDTOMapper;
import net.ow.movie.theatre.mapper.movie.TMDBDiscoverMovieRequestMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.discover.TMDBDiscoverMovieRequest;
import net.ow.movie.tmdb.model.movie.TMDBBaseMovie;
import net.ow.movie.tmdb.model.movie.TMDBMovie;
import net.ow.movie.tmdb.model.trending.TMDBTrendingMovie;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {
    private final TMDBFeignClient tmdbFeignClient;

    private final BaseMovieDTOMapper baseMovieDTOMapper;

    private final MovieDTOMapper movieDTOMapper;

    private final TMDBDiscoverMovieRequestMapper tmdbDiscoverMovieRequestMapper;

    private final GenreService genreService;

    public PaginatedResponse<BaseMovieDTO> getTrendingMovies(
            String timeWindow, String language, Integer page) {
        TMDBPaginatedResponse<TMDBTrendingMovie> tmdbPaginatedResponse =
                tmdbFeignClient.getTrendingMovies(timeWindow, language, page);

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

    public MovieDTO getMovieDetails(Integer movieId, String language) {
        StringJoiner stringJoiner = new StringJoiner(",");
        stringJoiner.add(CREDITS);
        stringJoiner.add(RECOMMENDATIONS);
        String appendToResponse = stringJoiner.toString();
        TMDBMovie tmdbMovie = tmdbFeignClient.getMovieDetails(movieId, appendToResponse, language);

        MovieDTO movie = movieDTOMapper.fromTMDBMovie(tmdbMovie);

        // NOTE: When fetching recommended movies from TMDB,
        // only ids are included in the response for genres.
        Map<Integer, GenreDTO> genreIdToGenreMap = genreService.getMovieGenresAsMap(language);
        movie.getRecommendations()
                .forEach(
                        recommendedMovie ->
                                enrichBaseMovieWithGenres(recommendedMovie, genreIdToGenreMap));

        return movie;
    }

    public PaginatedResponse<BaseMovieDTO> discoverMovies(
            DiscoverMovieRequest discoverMovieRequest, String language, Integer page) {
        TMDBDiscoverMovieRequest tmdbDiscoverMovieRequest =
                tmdbDiscoverMovieRequestMapper.fromDiscoverMovieRequest(
                        discoverMovieRequest, language, page);
        tmdbDiscoverMovieRequest.setIncludeAdult(TMDBConstant.INCLUDE_ADULT);

        TMDBPaginatedResponse<TMDBBaseMovie> tmdbPaginatedResponse =
                tmdbFeignClient.discoverMovies(tmdbDiscoverMovieRequest);

        PaginatedResponse<BaseMovieDTO> paginatedResponse =
                baseMovieDTOMapper.fromTMDBPaginatedBaseMovies(tmdbPaginatedResponse);

        // NOTE: When discovering movies from TMDB,
        // only ids are included in the response for genres.
        Map<Integer, GenreDTO> genreIdToGenreMap = genreService.getMovieGenresAsMap(language);
        paginatedResponse
                .getData()
                .forEach(movie -> enrichBaseMovieWithGenres(movie, genreIdToGenreMap));

        return paginatedResponse;
    }

    private void enrichBaseMovieWithGenres(
            BaseMovieDTO baseMovie, Map<Integer, GenreDTO> movieGenresMap) {
        List<Integer> genreIds = baseMovie.getGenres().stream().map(GenreDTO::getId).toList();
        List<GenreDTO> genres = genreIds.stream().map(movieGenresMap::get).toList();
        baseMovie.setGenres(genres);
    }
}
