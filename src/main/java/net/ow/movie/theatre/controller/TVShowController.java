package net.ow.movie.theatre.controller;

import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.trending.TrendingTVShowDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/tv-shows")
public class TVShowController {
    @GetMapping("/trending/{time_window}")
    public ResponseEntity<PaginatedResponse<TrendingTVShowDTO>> getTrendingTVShows(
            @PathVariable(value = "time_window") String timeWindow,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        throw new UnsupportedOperationException();
    }
}
