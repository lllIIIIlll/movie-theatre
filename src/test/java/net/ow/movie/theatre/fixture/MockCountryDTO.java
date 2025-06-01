package net.ow.movie.theatre.fixture;

import net.ow.movie.theatre.dto.country.CountryDTO;

public class MockCountryDTO {
    public static CountryDTO mock(String isoCode, String name) {
        CountryDTO country = new CountryDTO();

        country.setIosCode(isoCode);
        country.setName(name);

        return country;
    }
}
