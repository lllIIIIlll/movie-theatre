package net.ow.movie.theatre.controller;

import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.trending.TrendingMovieDTO;
import net.ow.movie.theatre.service.MovieService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/popular")
    public ResponseEntity<PaginatedResponse<BaseMovieDTO>> getPopularMovies(
            @RequestParam(required = false) String region,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(movieService.getPopularMovies(language, page, region));
    }

    @GetMapping("/now-playing")
    public ResponseEntity<PaginatedResponse<BaseMovieDTO>> getNowPlayingMovies(
            @RequestParam(required = false) String region,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(movieService.getNowPlayingMovies(language, page, region));
    }

    @GetMapping("/trending/{time_window}")
    public ResponseEntity<PaginatedResponse<TrendingMovieDTO>> getTrendingMovies(
            @PathVariable(value = "time_window") String timeWindow,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(movieService.getTrendingMovies(timeWindow, language, page));
    }
}
