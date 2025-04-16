package net.ow.movie.theatre.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.movie.MovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.service.MovieService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/popular")
    public ResponseEntity<PaginatedResponse<BaseMovieDTO>> getPopularMovies(
            @RequestParam(required = false) String region,
            @RequestParam(required = false, defaultValue = "1")
                    @Valid
                    @Min(value = 1, message = "Page must be at least 1")
                    @Max(value = 500, message = "Page must be less then 500")
                    Integer page,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(movieService.getPopularMovies(language, page, region));
    }

    @GetMapping("/now-playing")
    public ResponseEntity<PaginatedResponse<BaseMovieDTO>> getNowPlayingMovies(
            @RequestParam(required = false) String region,
            @RequestParam(required = false, defaultValue = "1")
                    @Valid
                    @Min(value = 1, message = "Page must be at least 1")
                    @Max(value = 500, message = "Page must be less then 500")
                    Integer page,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(movieService.getNowPlayingMovies(language, page, region));
    }

    @GetMapping("/trending")
    public ResponseEntity<PaginatedResponse<BaseMovieDTO>> getTrendingMovies(
            @RequestParam(value = "time_window") String timeWindow,
            @RequestParam(required = false, defaultValue = "1")
                    @Valid
                    @Min(value = 1, message = "Page must be at least 1")
                    @Max(value = 500, message = "Page must be less then 500")
                    Integer page,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(movieService.getTrendingMovies(timeWindow, language, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(
            @PathVariable Integer id,
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        throw new UnsupportedOperationException();
    }
}
