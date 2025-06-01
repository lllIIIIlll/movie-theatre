package net.ow.movie.theatre.fixture;

import net.ow.movie.theatre.dto.country.CountryDTO;
import net.ow.movie.tmdb.model.country.TMDBCountry;

public class MockTMDBCountry {
    public static TMDBCountry mock(String isoCode, String nativeName) {
        TMDBCountry country = new TMDBCountry();

        country.setIso31661(isoCode);
        country.setNativeName(nativeName);

        return country;
    }
}
