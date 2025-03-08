package net.ow.movie.theatre.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ow.movie.theatre.constant.TMDBConstant;
import net.ow.movie.theatre.dto.common.PagedResponse;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.mapper.SearchResultPagedResponseToSearchResultDTOPagedResponseMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.search.SearchResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {
    private final TMDBFeignClient tmdbFeignClient;

    private final SearchResultPagedResponseToSearchResultDTOPagedResponseMapper
            searchResultPagedResponseToSearchResultDTOPagedResponseMapper;

    public PagedResponse<SearchResultDTO> search(
            String query, String language, Integer pageNumber) {
        log.debug(
                "Searching - {} from TMDB.  Page - {} Language - {}", query, pageNumber, language);
        net.ow.movie.tmdb.model.common.PagedResponse<SearchResult> data =
                tmdbFeignClient.search(query, TMDBConstant.INCLUDE_ADULT, language, pageNumber);
        log.debug(
                "Searched - {} from TMDB.  Total - {} Total Page - {} Current page - {} Language - {}",
                query,
                data.getTotalResults(),
                data.getTotalPages(),
                data.getPage(),
                language);

        return searchResultPagedResponseToSearchResultDTOPagedResponseMapper.mapTo(data);
    }
}
