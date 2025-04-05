package net.ow.movie.theatre.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.trending.TrendingDTO;
import net.ow.movie.theatre.service.TrendingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/trending")
public class TrendingController {
    private final TrendingService trendingService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<TrendingDTO>> getTrendingContent(
            @RequestParam("time_window") String timeWindow,
            @RequestParam(required = false, defaultValue = "1")
                    @Valid
                    @Min(value = 1, message = "Page must be at least 1")
                    @Max(value = 500, message = "Page must be less then 500")
                    Integer page,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(trendingService.getTrendingContent(timeWindow, page, language));
    }
}
