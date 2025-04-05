package net.ow.movie.theatre.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.service.SearchService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<PaginatedResponse<? extends SearchResultDTO>> search(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "1")
                    @Valid
                    @Min(value = 1, message = "Page must be at least 1")
                    @Max(value = 500, message = "Page must be less then 500")
                    Integer pageNumber,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(searchService.search(query, pageNumber, language));
    }
}
