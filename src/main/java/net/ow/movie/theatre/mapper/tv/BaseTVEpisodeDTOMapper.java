package net.ow.movie.theatre.mapper.tv;

import net.ow.movie.theatre.dto.tv.BaseTVEpisodeDTO;
import net.ow.movie.tmdb.model.tv.TMDBTVEpisode;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseTVEpisodeDTOMapper {
    @Mapping(target = "rating", source = "voteAverage")
    @Mapping(target = "posterPath", source = "stillPath")
    BaseTVEpisodeDTO fromTMDBTVEpisode(TMDBTVEpisode tmdbtvEpisode);
}
