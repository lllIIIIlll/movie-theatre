package net.ow.movie.theatre.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.tv.BaseTVShowDTO;
import net.ow.movie.theatre.dto.tv.TVShowDTO;
import net.ow.movie.theatre.service.TVShowService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/tv-shows")
public class TVShowController {
    private final TVShowService tvShowService;

    @GetMapping("/trending")
    public ResponseEntity<PaginatedResponse<BaseTVShowDTO>> getTrendingTVShows(
            @RequestParam(value = "time_window") String timeWindow,
            @RequestParam(required = false, defaultValue = "1")
                    @Valid
                    @Min(value = 1, message = "Page must be at least 1")
                    @Max(value = 500, message = "Page must be less then 500")
                    Integer page,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(tvShowService.getTrendingTVShows(timeWindow, page, language));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TVShowDTO> getTVShowById(
            @PathVariable Integer id,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(tvShowService.getTVShowById(id, language));
    }
}
