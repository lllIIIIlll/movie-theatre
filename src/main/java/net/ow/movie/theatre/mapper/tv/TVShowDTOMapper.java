package net.ow.movie.theatre.mapper.tv;

import net.ow.movie.theatre.dto.tv.TVShowDTO;
import net.ow.movie.theatre.mapper.genre.GenreDTOMapper;
import net.ow.movie.tmdb.constant.TMDBMediaType;
import net.ow.movie.tmdb.model.tv.TMDBTVShow;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {GenreDTOMapper.class, BaseTVShowDTOMapper.class, BaseTVSeasonDTOMapper.class})
public interface TVShowDTOMapper {
    @Mapping(target = "releaseDate", source = "firstAirDate")
    @Mapping(target = "rating", source = "voteAverage")
    @Mapping(target = "mediaType", constant = TMDBMediaType.TV_SHOW)
    TVShowDTO fromTMDBTVShow(TMDBTVShow tmdbTVShow);
}
