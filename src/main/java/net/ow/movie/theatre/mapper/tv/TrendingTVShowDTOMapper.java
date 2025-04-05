package net.ow.movie.theatre.mapper.tv;

import net.ow.movie.theatre.dto.trending.TrendingTVShowDTO;
import net.ow.movie.theatre.mapper.genre.GenreDTOMapper;
import net.ow.movie.tmdb.model.trending.TMDBTrendingTVShow;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {GenreDTOMapper.class})
public interface TrendingTVShowDTOMapper {
    @Mapping(target = "releaseDate", source = "firstAirDate")
    @Mapping(target = "genres", source = "genreIds")
    @Mapping(target = "rating", source = "voteAverage")
    TrendingTVShowDTO fromTMDBTrendingTVShow(TMDBTrendingTVShow tmdbTrendingTVShow);
}
