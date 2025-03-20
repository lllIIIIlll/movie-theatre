package net.ow.movie.theatre.controller;

import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.service.SearchService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<PaginatedResponse<? extends SearchResultDTO>> search(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(searchService.search(query, pageNumber, language));
    }
}
