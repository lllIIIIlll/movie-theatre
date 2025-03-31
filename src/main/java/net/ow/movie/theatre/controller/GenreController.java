package net.ow.movie.theatre.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    @GetMapping("/movie")
    public ResponseEntity<List<GenreDTO>> getMovieGenres(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        throw new UnsupportedOperationException();
    }
}
