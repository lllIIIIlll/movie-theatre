package net.ow.movie.theatre.mapper.tv;

import net.ow.movie.theatre.dto.tv.TVSeasonDTO;
import net.ow.movie.tmdb.model.tv.TMDBTVSeason;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {BaseTVEpisodeDTOMapper.class})
public interface TVSeasonDTOMapper {
    @Mapping(target = "releaseDate", source = "airDate")
    @Mapping(target = "rating", source = "voteAverage")
    TVSeasonDTO fromTMDBTVSeason(TMDBTVSeason tmdbtvSeason);
}
