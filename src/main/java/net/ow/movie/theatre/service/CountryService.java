package net.ow.movie.theatre.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.ow.movie.theatre.dto.country.CountryDTO;
import net.ow.movie.theatre.mapper.country.CountryDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.country.TMDBCountry;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final TMDBFeignClient tmdbFeignClient;

    private final CountryDTOMapper countryDTOMapper;

    public List<CountryDTO> getCountries(String language) {
        List<TMDBCountry> tmdbCountries = tmdbFeignClient.getCountries(language);
        return countryDTOMapper.fromTMDBCountries(tmdbCountries);
    }
}
