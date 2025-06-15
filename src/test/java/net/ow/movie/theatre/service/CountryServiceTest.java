package net.ow.movie.theatre.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import net.ow.movie.theatre.dto.country.CountryDTO;
import net.ow.movie.theatre.mapper.country.CountryDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.country.TMDBCountry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {
    @InjectMocks private CountryService countryService;

    @Mock private TMDBFeignClient tmdbFeignClient;

    @Mock private CountryDTOMapper countryDTOMapper;

    @Test
    void getCountriesTest_OK() {
        String language = "en";
        String isoCode = "US";
        String countryName = "United States";

        TMDBCountry tmdbCountry = new TMDBCountry();
        tmdbCountry.setIso31661(isoCode);
        tmdbCountry.setNativeName(countryName);

        List<TMDBCountry> tmdbCountries = List.of(tmdbCountry);

        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setIosCode(isoCode);
        countryDTO.setName(countryName);

        List<CountryDTO> expectedDTOs = List.of(countryDTO);

        when(tmdbFeignClient.getCountries(language)).thenReturn(tmdbCountries);
        when(countryDTOMapper.fromTMDBCountries(tmdbCountries)).thenReturn(expectedDTOs);

        List<CountryDTO> result = countryService.getCountries(language);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(isoCode, result.get(0).getIosCode());
        assertEquals(countryName, result.get(0).getName());

        verify(tmdbFeignClient, times(1)).getCountries(language);
        verify(countryDTOMapper, times(1)).fromTMDBCountries(tmdbCountries);
    }

    @Test
    void getCountriesTest_whenEmptyTMDBCountries_thenReturnEmptyList() {
        String language = "zh";

        when(tmdbFeignClient.getCountries(language)).thenReturn(Collections.emptyList());
        when(countryDTOMapper.fromTMDBCountries(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        List<CountryDTO> result = countryService.getCountries(language);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(tmdbFeignClient, times(1)).getCountries(language);
        verify(countryDTOMapper, times(1)).fromTMDBCountries(Collections.emptyList());
    }
}
