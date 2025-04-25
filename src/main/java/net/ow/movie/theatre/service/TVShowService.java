package net.ow.movie.theatre.service;

import static net.ow.movie.theatre.constant.TMDBConstant.CREDITS;
import static net.ow.movie.theatre.constant.TMDBConstant.RECOMMENDATIONS;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.tv.BaseTVShowDTO;
import net.ow.movie.theatre.dto.tv.TVShowDTO;
import net.ow.movie.theatre.mapper.tv.BaseTVShowDTOMapper;
import net.ow.movie.theatre.mapper.tv.TVShowDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.trending.TMDBTrendingTVShow;
import net.ow.movie.tmdb.model.tv.TMDBTVShow;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TVShowService {
    private final TMDBFeignClient tmdbFeignClient;

    private final GenreService genreService;

    private final BaseTVShowDTOMapper paginatedResponseMapper;

    private final TVShowDTOMapper tvShowDTOMapper;

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

    public TVShowDTO getTVShowById(Integer tvShowId, String language) {
        StringJoiner stringJoiner = new StringJoiner(",");
        stringJoiner.add(CREDITS);
        stringJoiner.add(RECOMMENDATIONS);
        String appendToResponse = stringJoiner.toString();

        TMDBTVShow tmdbtvShow =
                tmdbFeignClient.getTVShowDetails(tvShowId, appendToResponse, language);

        TVShowDTO tvShow = tvShowDTOMapper.fromTMDBTVShow(tmdbtvShow);

        // NOTE: When fetching recommended tv show from TMDB,
        // only ids are included in the response for genres.
        Map<Integer, GenreDTO> genreIdToGenreMap = genreService.getMovieGenresAsMap(language);
        tvShow.getRecommendations()
                .forEach(
                        recommendedTVShow ->
                                enrichBaseTVShowWithGenres(recommendedTVShow, genreIdToGenreMap));

        return tvShow;
    }

    private void enrichBaseTVShowWithGenres(
            BaseTVShowDTO baseTVShow, Map<Integer, GenreDTO> genreIdToGenreMap) {
        List<Integer> genreIds = baseTVShow.getGenres().stream().map(GenreDTO::getId).toList();
        List<GenreDTO> genres =
                genreIds.stream().map(genreIdToGenreMap::get).filter(Objects::nonNull).toList();
        baseTVShow.setGenres(genres);
    }
}
