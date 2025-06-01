package net.ow.movie.theatre.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.country.CountryDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/configuration")
public class ConfigurationController {
    @GetMapping("/countries")
    public ResponseEntity<List<CountryDTO>> getCountries(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String language) {
        throw new UnsupportedOperationException();
    }
}
