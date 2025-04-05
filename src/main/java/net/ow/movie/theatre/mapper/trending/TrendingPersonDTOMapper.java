package net.ow.movie.theatre.mapper.trending;

import net.ow.movie.theatre.dto.trending.TrendingPersonDTO;
import net.ow.movie.tmdb.model.trending.TMDBTrendingPerson;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TrendingPersonDTOMapper {
    TrendingPersonDTO fromTMDBTrendingPerson(TMDBTrendingPerson tmdbTrendingPerson);
}
