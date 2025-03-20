package net.ow.movie.theatre.controller;

import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {
    @GetMapping("/search")
    public ResponseEntity<PaginatedResponse<? extends SearchResultDTO>> search(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        throw new UnsupportedOperationException();
    }
}
