package net.ow.movie.theatre.mapper.tv;

import net.ow.movie.theatre.dto.tv.BaseTVSeasonDTO;
import net.ow.movie.tmdb.model.tv.TMDBBaseTVSeason;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseTVSeasonDTOMapper {
    @Mapping(target = "releaseDate", source = "airDate")
    @Mapping(target = "rating", source = "voteAverage")
    BaseTVSeasonDTO fromTMDBBaseTVSeason(TMDBBaseTVSeason tmdbBaseTVSeason);
}
