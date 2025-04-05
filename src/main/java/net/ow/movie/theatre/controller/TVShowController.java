package net.ow.movie.theatre.controller;

import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.trending.TrendingTVShowDTO;
import net.ow.movie.theatre.service.TVShowService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/tv-shows")
public class TVShowController {
    private final TVShowService tvShowService;

    @GetMapping("/trending/{time_window}")
    public ResponseEntity<PaginatedResponse<TrendingTVShowDTO>> getTrendingTVShows(
            @PathVariable(value = "time_window") String timeWindow,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(tvShowService.getTrendingTVShows(timeWindow, page, language));
    }
}
