package net.ow.movie.theatre.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ow.movie.theatre.constant.TMDBConstant;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.mapper.PaginatedResponseMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.search.TMDBSearchResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {
    private final TMDBFeignClient tmdbFeignClient;

    private final PaginatedResponseMapper paginatedResponseMapper;

    public PaginatedResponse<SearchResultDTO> search(
            String query, Integer pageNumber, String language) {
        log.debug("Searching \"{}\" from tmdb", query);
        TMDBPaginatedResponse<TMDBSearchResult> tmdbPaginatedResponse =
                tmdbFeignClient.search(query, TMDBConstant.INCLUDE_ADULT, language, pageNumber);
        log.debug("Searched \"{}\" from tmdb", query);

        return paginatedResponseMapper.fromTMDBPaginatedSearchResults(tmdbPaginatedResponse);
    }
}
