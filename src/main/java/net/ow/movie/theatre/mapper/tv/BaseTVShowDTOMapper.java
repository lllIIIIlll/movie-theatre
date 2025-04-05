package net.ow.movie.theatre.mapper.tv;

import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.tv.BaseTVShowDTO;
import net.ow.movie.theatre.mapper.genre.GenreDTOMapper;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.trending.TMDBTrendingTVShow;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {GenreDTOMapper.class})
public interface BaseTVShowDTOMapper {
    @Mapping(target = "releaseDate", source = "firstAirDate")
    @Mapping(target = "genres", source = "genreIds")
    @Mapping(target = "rating", source = "voteAverage")
    BaseTVShowDTO fromTMDBTrendingTVShow(TMDBTrendingTVShow tmdbTrendingTVShow);

    @Mapping(target = "data", source = "results")
    @Mapping(target = "total", source = "totalResults")
    PaginatedResponse<BaseTVShowDTO> fromTMDBPaginatedTrendingTVShows(
            TMDBPaginatedResponse<TMDBTrendingTVShow> tmdbPaginatedResponse);
}
