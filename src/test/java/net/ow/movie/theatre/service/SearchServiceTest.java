package net.ow.movie.theatre.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import feign.FeignException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.fixture.*;
import net.ow.movie.theatre.mapper.search.SearchResultDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.search.TMDBPersonSearchResult;
import net.ow.movie.tmdb.model.search.TMDBSearchResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {
    @InjectMocks private SearchService searchService;

    @Mock private TMDBFeignClient tmdbFeignClient;

    @Mock private GenreService genreService;

    @Mock private SearchResultDTOMapper searchResultDTOMapper;

    @Test
    void searchTest_OK() {
        String query = "query";
        Integer page = 1;
        String language = "en-US";

        Integer genreId = 1;
        String genreName = "genre-name";

        Integer movieId = 1;
        String movieName = "movie-name";
        Integer tvShowId = 2;
        String tvShowName = "tv-show-name";
        Integer personId = 1;
        String personName = "person-name";

        GenreDTO genre = MockGenreDTO.mock(genreId, genreName);
        Map<Integer, GenreDTO> genreIdToGenreMap = Collections.singletonMap(genreId, genre);

        TMDBSearchResult tmdbMovieSearchResult =
                MockTMDBMovieSearchResult.mock(movieId, movieName, List.of(genreId));
        TMDBSearchResult tmdbTVShowSearchResult =
                MockTMDBMovieSearchResult.mock(tvShowId, tvShowName, List.of(genreId));
        TMDBPersonSearchResult tmdbPersonSearchResult =
                MockTMDBPersonSearchResult.mock(personId, personName);
        TMDBPaginatedResponse<TMDBSearchResult> tmdbPaginatedResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedSearchResult(
                        List.of(
                                tmdbMovieSearchResult,
                                tmdbTVShowSearchResult,
                                tmdbPersonSearchResult));

        SearchResultDTO movieSearchResult =
                MockSearchResultDTO.mock(
                        movieId, movieName, List.of(MockGenreDTO.mock(genreId, null)));
        SearchResultDTO tvShowSearchResult =
                MockSearchResultDTO.mock(
                        tvShowId, tvShowName, List.of(MockGenreDTO.mock(genreId, null)));
        SearchResultDTO personSearchResult = MockSearchResultDTO.mock(personId, personName);
        PaginatedResponse<SearchResultDTO> paginatedResponse =
                MockPaginatedResponse.mockPaginatedSearchResultDTO(
                        List.of(movieSearchResult, tvShowSearchResult, personSearchResult));

        when(tmdbFeignClient.search(query, true, language, page)).thenReturn(tmdbPaginatedResponse);
        when(searchResultDTOMapper.fromTMDBPaginatedSearchResults(tmdbPaginatedResponse))
                .thenReturn(paginatedResponse);
        when(genreService.getGenresAsMap(language)).thenReturn(genreIdToGenreMap);

        PaginatedResponse<SearchResultDTO> actualPaginatedResponse =
                searchService.search(query, page, language);

        assertNotNull(actualPaginatedResponse);
        assertEquals(genre, movieSearchResult.getGenres().get(0));
        assertEquals(genre, tvShowSearchResult.getGenres().get(0));
        assertNull(personSearchResult.getGenres());

        verify(tmdbFeignClient, times(1)).search(query, true, language, page);
        verify(searchResultDTOMapper, times(1))
                .fromTMDBPaginatedSearchResults(tmdbPaginatedResponse);
        verify(genreService, times(1)).getGenresAsMap(language);
    }

    @Test
    void searchTest_whenNullTMDBResponse_thenReturnsEmptyPaginatedResponse() {
        String query = "query";
        Integer page = 1;
        String language = "en-US";

        TMDBPaginatedResponse<TMDBSearchResult> tmdbPaginatedResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedSearchResult(Collections.emptyList());
        PaginatedResponse<SearchResultDTO> paginatedResponse =
                MockPaginatedResponse.mockPaginatedSearchResultDTO(Collections.emptyList());

        when(tmdbFeignClient.search(query, true, language, page)).thenReturn(tmdbPaginatedResponse);
        when(searchResultDTOMapper.fromTMDBPaginatedSearchResults(tmdbPaginatedResponse))
                .thenReturn(paginatedResponse);
        when(genreService.getGenresAsMap(language)).thenReturn(Collections.emptyMap());

        PaginatedResponse<SearchResultDTO> actualPaginatedResponse =
                searchService.search(query, page, language);

        assertTrue(actualPaginatedResponse.getData().isEmpty());
        assertEquals(1, actualPaginatedResponse.getPage());
        assertEquals(1, actualPaginatedResponse.getTotalPages());
        assertEquals(0, actualPaginatedResponse.getTotal());

        verify(tmdbFeignClient, times(1)).search(query, true, language, page);
        verify(searchResultDTOMapper, times(1))
                .fromTMDBPaginatedSearchResults(tmdbPaginatedResponse);
        verify(genreService, times(1)).getGenresAsMap(language);
    }

    @Test
    void searchTest_whenThrowFeignException_thenThrowFeignException() {
        String query = "query";
        Integer page = 1;
        String language = "en-US";

        when(tmdbFeignClient.search(query, true, language, page)).thenThrow(FeignException.class);

        assertThrows(FeignException.class, () -> searchService.search(query, page, language));
        verify(tmdbFeignClient, times(1)).search(query, true, language, page);
        verify(searchResultDTOMapper, never()).fromTMDBPaginatedSearchResults(any());
    }
}
