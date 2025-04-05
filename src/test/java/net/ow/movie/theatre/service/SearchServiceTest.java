package net.ow.movie.theatre.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import feign.FeignException;
import java.util.Collections;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.fixture.MockPaginatedResponse;
import net.ow.movie.theatre.fixture.MockTMDBPaginatedResponse;
import net.ow.movie.theatre.mapper.search.SearchResultDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
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

    @Mock private SearchResultDTOMapper searchResultDTOMapper;

    @Test
    void searchTest_OK() {
        String query = "query";
        Integer page = 1;
        String language = "en-US";

        TMDBPaginatedResponse<TMDBSearchResult> tmdbPaginatedResponse =
                MockTMDBPaginatedResponse.mockTMDBPaginatedSearchResult(Collections.emptyList());
        PaginatedResponse<SearchResultDTO> paginatedResponse =
                MockPaginatedResponse.mockPaginatedSearchResult(Collections.emptyList());

        when(tmdbFeignClient.search(query, true, language, page)).thenReturn(tmdbPaginatedResponse);
        when(searchResultDTOMapper.fromTMDBPaginatedSearchResults(tmdbPaginatedResponse))
                .thenReturn(paginatedResponse);

        PaginatedResponse<SearchResultDTO> actualPaginatedResponse =
                searchService.search(query, page, language);

        assertEquals(actualPaginatedResponse, paginatedResponse);
        verify(tmdbFeignClient, times(1)).search(query, true, language, page);
        verify(searchResultDTOMapper, times(1))
                .fromTMDBPaginatedSearchResults(tmdbPaginatedResponse);
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
