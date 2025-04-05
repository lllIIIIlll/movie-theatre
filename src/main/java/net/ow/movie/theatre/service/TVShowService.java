package net.ow.movie.theatre.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.tv.BaseTVShowDTO;
import net.ow.movie.theatre.mapper.tv.BaseTVShowDTOMapper;
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

    private final BaseTVShowDTOMapper paginatedResponseMapper;

    public PaginatedResponse<BaseTVShowDTO> getTrendingTVShows(
            String timeWindow, Integer page, String language) {
        log.debug("Fetching trending TV shows from TMDB");
        TMDBPaginatedResponse<TMDBTrendingTVShow> tmdbPaginatedResponse =
                tmdbFeignClient.getTrendingTVShows(timeWindow, language, page);
        log.debug("Fetched trending TV shows from TMDB");

        PaginatedResponse<BaseTVShowDTO> paginatedResponse =
                paginatedResponseMapper.fromTMDBPaginatedTrendingTVShows(tmdbPaginatedResponse);

        // NOTE: When fetching trending tv show from TMDB,
        // only ids are included in the response for genres.
        Map<Integer, GenreDTO> genreIdToGenreMap = genreService.getTVShowGenresAsMap(language);
        paginatedResponse
                .getData()
                .forEach(tvShow -> enrichBaseTVShowWithGenres(tvShow, genreIdToGenreMap));

        return paginatedResponse;
    }

    private void enrichBaseTVShowWithGenres(
            BaseTVShowDTO baseTVShow, Map<Integer, GenreDTO> genreIdToGenreMap) {
        List<Integer> genreIds = baseTVShow.getGenres().stream().map(GenreDTO::getId).toList();
        List<GenreDTO> genres =
                genreIds.stream().map(genreIdToGenreMap::get).filter(Objects::nonNull).toList();
        baseTVShow.setGenres(genres);
    }
}
