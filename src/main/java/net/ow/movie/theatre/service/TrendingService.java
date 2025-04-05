package net.ow.movie.theatre.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.trending.TrendingDTO;
import net.ow.movie.theatre.dto.trending.TrendingMovieDTO;
import net.ow.movie.theatre.dto.trending.TrendingTVShowDTO;
import net.ow.movie.theatre.mapper.PaginatedResponseMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.trending.TMDBTrending;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrendingService {
    private final TMDBFeignClient tmdbFeignClient;

    private final GenreService genreService;

    private final PaginatedResponseMapper paginatedResponseMapper;

    public PaginatedResponse<TrendingDTO> getTrendingContent(
            String timeWindow, Integer page, String language) {
        log.debug("Fetching trending content from TMDB.");
        TMDBPaginatedResponse<TMDBTrending> tmdbPaginatedResponse =
                tmdbFeignClient.getTrending(timeWindow, language, page);
        log.debug("Fetched trending content from TMDB.");

        PaginatedResponse<TrendingDTO> paginatedResponse =
                paginatedResponseMapper.fromTMDBPaginatedTrending(tmdbPaginatedResponse);

        // NOTE: When fetching trending content from TMDB,
        // only ids are included in the response for genres.
        Map<Integer, GenreDTO> movieGenresMap = genreService.getMovieGenresAsMap(language);
        Map<Integer, GenreDTO> tvShowGenresMap = genreService.getTVShowGenresAsMap(language);
        enrichTrendingContentWithGenres(paginatedResponse, movieGenresMap, tvShowGenresMap);

        return paginatedResponse;
    }

    private void enrichTrendingContentWithGenres(
            PaginatedResponse<TrendingDTO> paginatedResponse,
            Map<Integer, GenreDTO> movieGenresMap,
            Map<Integer, GenreDTO> tvShowGenresMap) {
        log.debug("Enriching trending content with genre details.");

        paginatedResponse
                .getData()
                .forEach(
                        trending -> {
                            if (trending instanceof TrendingMovieDTO movie) {
                                enrichMovieWithGenres(movie, movieGenresMap);
                            }

                            if (trending instanceof TrendingTVShowDTO tvShow) {
                                enrichTvShowWithGenres(tvShow, tvShowGenresMap);
                            }
                        });
    }

    private void enrichMovieWithGenres(
            TrendingMovieDTO trendingMovie, Map<Integer, GenreDTO> genresMap) {
        List<Integer> genreIds = trendingMovie.getGenres().stream().map(GenreDTO::getId).toList();
        List<GenreDTO> genres =
                genreIds.stream().map(genresMap::get).filter(Objects::nonNull).toList();
        trendingMovie.setGenres(genres);
    }

    private void enrichTvShowWithGenres(
            TrendingTVShowDTO trendingTVShow, Map<Integer, GenreDTO> genresMap) {
        List<Integer> genreIds = trendingTVShow.getGenres().stream().map(GenreDTO::getId).toList();
        List<GenreDTO> genres =
                genreIds.stream().map(genresMap::get).filter(Objects::nonNull).toList();
        trendingTVShow.setGenres(genres);
    }
}
