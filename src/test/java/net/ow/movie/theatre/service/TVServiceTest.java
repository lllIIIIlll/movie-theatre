package net.ow.movie.theatre.service;

import static net.ow.movie.theatre.constant.TMDBConstant.CREDITS;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import feign.FeignException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.tv.BaseTVShowDTO;
import net.ow.movie.theatre.dto.tv.TVSeasonDTO;
import net.ow.movie.theatre.dto.tv.TVShowDTO;
import net.ow.movie.theatre.fixture.*;
import net.ow.movie.theatre.mapper.tv.BaseTVShowDTOMapper;
import net.ow.movie.theatre.mapper.tv.TVSeasonDTOMapper;
import net.ow.movie.theatre.mapper.tv.TVShowDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.trending.TMDBTrendingTVShow;
import net.ow.movie.tmdb.model.tv.TMDBTVSeason;
import net.ow.movie.tmdb.model.tv.TMDBTVShow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TVServiceTest {
    @InjectMocks private TVService tvService;

    @Mock private TMDBFeignClient tmdbFeignClient;

    @Mock private BaseTVShowDTOMapper baseTVShowDTOMapper;

    @Mock private TVShowDTOMapper tvShowDTOMapper;

    @Mock private GenreService genreService;

    @Mock private TVSeasonDTOMapper tvSeasonDTOMapper;

    @Test
    void getTrendingTVShowsTest_OK() {
        String timeWindow = "day";
        String language = "zh-CN";
        Integer page = 1;

        Integer genreId = 1;
        String genreName = "genre-name";
        GenreDTO genre = MockGenreDTO.mock(genreId, genreName);
        Map<Integer, GenreDTO> genreIdToGenreMap = Collections.singletonMap(genreId, genre);

        Integer tvShowId = 1;
        String tvShowName = "tv-show-name";
        BaseTVShowDTO tvShow =
                MockBaseTVShowDTO.mock(
                        tvShowId, tvShowName, List.of(MockGenreDTO.mock(genreId, null)));
        PaginatedResponse<BaseTVShowDTO> paginatedResponse =
                MockPaginatedResponse.mockPaginatedBaseTVShowDTO(List.of(tvShow));

        TMDBTrendingTVShow trendingTVShow = MockTMDBTrendingTVShow.mock(tvShowId);
        TMDBPaginatedResponse<TMDBTrendingTVShow> tmdbPaginatedResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedTrendingTVShow(List.of(trendingTVShow));

        when(tmdbFeignClient.getTrendingTVShows(timeWindow, language, page))
                .thenReturn(tmdbPaginatedResponse);
        when(baseTVShowDTOMapper.fromTMDBPaginatedTrendingTVShows(tmdbPaginatedResponse))
                .thenReturn(paginatedResponse);

        when(genreService.getTVShowGenresAsMap(language)).thenReturn(genreIdToGenreMap);

        PaginatedResponse<BaseTVShowDTO> actualResponse =
                tvService.getTrendingTVShows(timeWindow, page, language);

        assertNotNull(actualResponse);

        assertFalse(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(genre, tvShow.getGenres().get(0));
    }

    @Test
    void getTrendingTVShowsTest_whenNullTMDBResponse_thenReturnsEmptyPaginatedResponse() {
        String timeWindow = "day";
        String language = "zh-CN";
        Integer page = 1;

        PaginatedResponse<BaseTVShowDTO> expectedPaginatedResponse =
                MockPaginatedResponse.mockPaginatedBaseTVShowDTO(Collections.emptyList());

        when(tmdbFeignClient.getTrendingTVShows(timeWindow, language, page)).thenReturn(null);
        when(baseTVShowDTOMapper.fromTMDBPaginatedTrendingTVShows(null))
                .thenReturn(expectedPaginatedResponse);

        PaginatedResponse<BaseTVShowDTO> actualResponse =
                tvService.getTrendingTVShows(timeWindow, page, language);

        assertTrue(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(1, actualResponse.getTotalPages());
        assertEquals(0, actualResponse.getTotal());
    }

    @Test
    void getTrendingTVShowsTest_whenTMDBEmptyData_thenReturnsEmptyPaginatedResponse() {
        String timeWindow = "day";
        String language = "zh-CN";
        Integer page = 1;

        TMDBPaginatedResponse<TMDBTrendingTVShow> tmdbPaginatedResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedTrendingTVShow(Collections.emptyList());

        PaginatedResponse<BaseTVShowDTO> expectedPaginatedResponse =
                MockPaginatedResponse.mockPaginatedBaseTVShowDTO(Collections.emptyList());

        when(tmdbFeignClient.getTrendingTVShows(timeWindow, language, page))
                .thenReturn(tmdbPaginatedResponse);
        when(baseTVShowDTOMapper.fromTMDBPaginatedTrendingTVShows(tmdbPaginatedResponse))
                .thenReturn(expectedPaginatedResponse);

        PaginatedResponse<BaseTVShowDTO> actualResponse =
                tvService.getTrendingTVShows(timeWindow, page, language);

        assertTrue(actualResponse.getData().isEmpty());
        assertEquals(1, actualResponse.getPage());
        assertEquals(1, actualResponse.getTotalPages());
        assertEquals(0, actualResponse.getTotal());
    }

    @Test
    void getTVShowByIdTest_OK() {
        Integer tvShowId = 1;
        String recommendedTvShowName = "recommended-tv-show-name";
        String tvShowName = "tv-show-name";
        String language = "zh-CN";
        String appendToResponse = "credits,recommendations";

        Integer genreId = 1;
        String genreName = "genre-name";
        GenreDTO genre = MockGenreDTO.mock(genreId, genreName);
        Map<Integer, GenreDTO> genreIdToGenreMap = Collections.singletonMap(genreId, genre);

        BaseTVShowDTO recommendedTVShow =
                MockBaseTVShowDTO.mock(
                        tvShowId, recommendedTvShowName, List.of(MockGenreDTO.mock(genreId, null)));
        TVShowDTO tvShow =
                MockTVShowDTO.mock(
                        tvShowId,
                        tvShowName,
                        List.of(MockGenreDTO.mock(genreId, null)),
                        List.of(recommendedTVShow));

        TMDBTVShow tmdbTVShow = MockTMDBTVShow.mock(tvShowId);

        when(tmdbFeignClient.getTVShowDetails(tvShowId, appendToResponse, language))
                .thenReturn(tmdbTVShow);
        when(tvShowDTOMapper.fromTMDBTVShow(tmdbTVShow)).thenReturn(tvShow);
        when(genreService.getTVShowGenresAsMap(language)).thenReturn(genreIdToGenreMap);

        TVShowDTO actualTVShow = tvService.getTVShowById(tvShowId, language);

        assertNotNull(actualTVShow);
        assertEquals(1, actualTVShow.getRecommendations().size());
        assertEquals(genre, actualTVShow.getRecommendations().get(0).getGenres().get(0));
    }

    @Test
    void getTVShowByIdTest_whenNoRecommendations_thenReturnsTVShowDTO() {
        Integer tvShowId = 1;
        String tvShowName = "tv-show-name";
        String language = "zh-CN";
        String appendToResponse = "credits,recommendations";

        Integer genreId = 1;
        String genreName = "genre-name";
        GenreDTO genre = MockGenreDTO.mock(genreId, genreName);
        Map<Integer, GenreDTO> genreIdToGenreMap = Collections.singletonMap(genreId, genre);

        TVShowDTO tvShow =
                MockTVShowDTO.mock(
                        tvShowId,
                        tvShowName,
                        List.of(MockGenreDTO.mock(genreId, null)),
                        Collections.emptyList());

        TMDBTVShow tmdbTVShow = MockTMDBTVShow.mock(tvShowId);

        when(tmdbFeignClient.getTVShowDetails(tvShowId, appendToResponse, language))
                .thenReturn(tmdbTVShow);
        when(tvShowDTOMapper.fromTMDBTVShow(tmdbTVShow)).thenReturn(tvShow);
        when(genreService.getTVShowGenresAsMap(language)).thenReturn(genreIdToGenreMap);

        TVShowDTO actualTVShow = tvService.getTVShowById(tvShowId, language);

        assertNotNull(actualTVShow);
        assertTrue(actualTVShow.getRecommendations().isEmpty());
    }

    @Test
    void getTVShowByIdTest_whenEmptyGenreMap_thenReturnsTVShowDTO() {
        Integer tvShowId = 1;
        String recommendedTvShowName = "recommended-tv-show-name";
        String tvShowName = "tv-show-name";
        String language = "zh-CN";
        String appendToResponse = "credits,recommendations";

        BaseTVShowDTO recommendedTVShow =
                MockBaseTVShowDTO.mock(
                        tvShowId, recommendedTvShowName, List.of(MockGenreDTO.mock(1, null)));
        TVShowDTO tvShow =
                MockTVShowDTO.mock(
                        tvShowId,
                        tvShowName,
                        List.of(MockGenreDTO.mock(1, null)),
                        List.of(recommendedTVShow));

        TMDBTVShow tmdbTVShow = MockTMDBTVShow.mock(tvShowId);

        when(tmdbFeignClient.getTVShowDetails(tvShowId, appendToResponse, language))
                .thenReturn(tmdbTVShow);
        when(tvShowDTOMapper.fromTMDBTVShow(tmdbTVShow)).thenReturn(tvShow);
        when(genreService.getTVShowGenresAsMap(language)).thenReturn(Collections.emptyMap());

        TVShowDTO actualTVShow = tvService.getTVShowById(tvShowId, language);

        assertNotNull(actualTVShow);
        assertEquals(1, actualTVShow.getRecommendations().size());
        assertTrue(actualTVShow.getRecommendations().get(0).getGenres().isEmpty());
    }

    @Test
    void getTVShowByIdTest_whenInvalidId_thenThrowException() {
        Integer tvShowId = 1;
        String language = "zh-CN";
        String appendToResponse = "credits,recommendations";

        when(tmdbFeignClient.getTVShowDetails(tvShowId, appendToResponse, language))
                .thenThrow(FeignException.class);

        assertThrows(FeignException.class, () -> tvService.getTVShowById(tvShowId, language));
    }

    @Test
    void getTVSeasonByTVShowIdAndSeasonNumberTest_OK() {
        Integer tvShowId = 1;
        Integer id = 1;
        String name = "tv-season-name";
        Integer seasonNumber = 1;
        String language = "zh-CN";

        TMDBTVSeason mockTMDBTVSeason = MockTMDBTVSeason.mock(id, name);
        TVSeasonDTO expectedTVSeasonDTO = MockTVSeasonDTO.mock(id, name);

        when(tmdbFeignClient.getTVSeasonDetails(tvShowId, seasonNumber, CREDITS, language))
                .thenReturn(mockTMDBTVSeason);
        when(tvSeasonDTOMapper.fromTMDBTVSeason(mockTMDBTVSeason)).thenReturn(expectedTVSeasonDTO);

        TVSeasonDTO actualTVSeasonDTO =
                tvService.getTVSeasonByTVShowIdAndSeasonNumber(tvShowId, seasonNumber, language);

        assertNotNull(actualTVSeasonDTO);
        assertEquals(expectedTVSeasonDTO, actualTVSeasonDTO);
    }

    @Test
    void getTVSeasonByTVShowIdAndSeasonNumberTest_whenNullResponse_thenReturnsNull() {
        Integer tvShowId = 1;
        Integer seasonNumber = 1;
        String language = "zh-CN";

        when(tmdbFeignClient.getTVSeasonDetails(tvShowId, seasonNumber, CREDITS, language))
                .thenReturn(null);

        TVSeasonDTO actualTVSeasonDTO =
                tvService.getTVSeasonByTVShowIdAndSeasonNumber(tvShowId, seasonNumber, language);

        assertNull(actualTVSeasonDTO);
    }

    @Test
    void
            getTVSeasonByTVShowIdAndSeasonNumberTest_whenThrowFeignException_thenThrowsFeignException() {
        Integer tvShowId = 1;
        Integer seasonNumber = 1;
        String language = "zh-CN";

        when(tmdbFeignClient.getTVSeasonDetails(tvShowId, seasonNumber, CREDITS, language))
                .thenThrow(FeignException.class);

        assertThrows(
                FeignException.class,
                () ->
                        tvService.getTVSeasonByTVShowIdAndSeasonNumber(
                                tvShowId, seasonNumber, language));
    }
}
