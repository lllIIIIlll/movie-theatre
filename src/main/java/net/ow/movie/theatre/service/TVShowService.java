package net.ow.movie.theatre.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.trending.TrendingTVShowDTO;
import net.ow.movie.theatre.mapper.PaginatedResponseMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.trending.TMDBTrendingTVShow;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TVShowService {
    private final TMDBFeignClient tmdbFeignClient;

    private final GenreService genreService;

    private final PaginatedResponseMapper paginatedResponseMapper;

    public PaginatedResponse<TrendingTVShowDTO> getTrendingTVShows(
            String timeWindow, Integer page, String language) {
        log.debug("Fetching trending TV shows from TMDB");
        TMDBPaginatedResponse<TMDBTrendingTVShow> tmdbPaginatedResponse =
                tmdbFeignClient.getTrendingTVShows(timeWindow, language, page);
        log.debug("Fetched trending TV shows from TMDB");

        PaginatedResponse<TrendingTVShowDTO> paginatedResponse =
                paginatedResponseMapper.fromTMDBPaginatedTrendingTVShows(tmdbPaginatedResponse);

        return enrichTrendingTVShowWithGenreDetails(paginatedResponse, language);
    }

    private PaginatedResponse<TrendingTVShowDTO> enrichTrendingTVShowWithGenreDetails(
            PaginatedResponse<TrendingTVShowDTO> paginatedResponse, String language) {
        // NOTE: When fetching trending tv show from TMDB,
        // only ids are included in the response for genres.
        Map<Integer, GenreDTO> genreIdToGenreMap = genreService.getTVShowGenresAsMap(language);
        paginatedResponse
                .getData()
                .forEach(
                        tvShow -> {
                            List<Integer> genreIds =
                                    tvShow.getGenres().stream().map(GenreDTO::getId).toList();
                            List<GenreDTO> genres =
                                    genreIds.stream().map(genreIdToGenreMap::get).toList();
                            tvShow.setGenres(genres);
                        });

        return paginatedResponse;
    }
}
