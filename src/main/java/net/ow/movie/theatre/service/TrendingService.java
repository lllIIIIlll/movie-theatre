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
        log.debug("Fetched trending content form TMDB.");

        PaginatedResponse<TrendingDTO> paginatedResponse =
                paginatedResponseMapper.fromTMDBPaginatedTrending(tmdbPaginatedResponse);

        // NOTE: When fetching trending content from TMDB,
        // only ids are included in the response for genres.
        Map<Integer, GenreDTO> genreIdToGenreMap = genreService.getAllGenresAsMap(language);
        enrichTrendingContentWithGenreDetails(paginatedResponse, genreIdToGenreMap);

        return paginatedResponse;
    }

    private void enrichTrendingContentWithGenreDetails(
            PaginatedResponse<TrendingDTO> paginatedResponse,
            Map<Integer, GenreDTO> genreIdToGenreMap) {
        log.debug("Enriching trending content with genre details.");

        paginatedResponse
                .getData()
                .forEach(
                        trending -> {
                            if (trending instanceof TrendingMovieDTO movie) {
                                enrichTrendingMovieWithGenreDetails(movie, genreIdToGenreMap);
                            }

                            if (trending instanceof TrendingTVShowDTO tvShow) {
                                enrichTrendingTVShowWithGenreDetails(tvShow, genreIdToGenreMap);
                            }
                        });
    }

    private void enrichTrendingMovieWithGenreDetails(
            TrendingMovieDTO trendingMovie, Map<Integer, GenreDTO> genreIdToGenreMap) {
        List<Integer> genreIds = trendingMovie.getGenres().stream().map(GenreDTO::getId).toList();
        List<GenreDTO> genres =
                genreIds.stream().map(genreIdToGenreMap::get).filter(Objects::nonNull).toList();
        trendingMovie.setGenres(genres);
    }

    private void enrichTrendingTVShowWithGenreDetails(
            TrendingTVShowDTO trendingTVShow, Map<Integer, GenreDTO> genreIdToGenreMap) {
        List<Integer> genreIds = trendingTVShow.getGenres().stream().map(GenreDTO::getId).toList();
        List<GenreDTO> genres =
                genreIds.stream().map(genreIdToGenreMap::get).filter(Objects::nonNull).toList();
        trendingTVShow.setGenres(genres);
    }
}
