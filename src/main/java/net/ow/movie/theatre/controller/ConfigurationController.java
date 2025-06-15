package net.ow.movie.theatre.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.country.CountryDTO;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.service.CountryService;
import net.ow.movie.theatre.service.GenreService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/configuration")
public class ConfigurationController {
    private final CountryService countryService;

    private final GenreService genreService;

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDTO>> getCountries(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(countryService.getCountries(language));
    }

    @GetMapping("/genres/movie")
    public ResponseEntity<List<GenreDTO>> getMovieGenres(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(genreService.getMovieGenres(language));
    }

    @GetMapping("/genres/tv-show")
    public ResponseEntity<List<GenreDTO>> getTVShowGenres(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(genreService.getTVShowGenres(language));
    }
}
