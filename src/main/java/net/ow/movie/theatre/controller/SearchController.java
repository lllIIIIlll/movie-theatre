package net.ow.movie.theatre.controller;

import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.common.PagedResponse;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.service.SearchService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/search")
    public PagedResponse<SearchResultDTO> search(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return searchService.search(query, language, pageNumber);
    }
}
