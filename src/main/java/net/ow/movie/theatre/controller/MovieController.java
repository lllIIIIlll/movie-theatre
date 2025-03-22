package net.ow.movie.theatre.controller;

import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    @GetMapping("/popular")
    public ResponseEntity<PaginatedResponse<BaseMovieDTO>> getPopularMovies() {
        throw new UnsupportedOperationException();
    }
}
