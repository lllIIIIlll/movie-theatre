package net.ow.movie.theatre.mapper.country;

import java.util.List;
import net.ow.movie.theatre.dto.country.CountryDTO;
import net.ow.movie.tmdb.model.country.TMDBCountry;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryDTOMapper {
    @Mapping(target = "iosCode", source = "iso31661")
    @Mapping(target = "name", source = "nativeName")
    CountryDTO fromTMDBCountry(TMDBCountry tmdbCountry);

    List<CountryDTO> fromTMDBCountries(List<TMDBCountry> tmdbCountries);
}
