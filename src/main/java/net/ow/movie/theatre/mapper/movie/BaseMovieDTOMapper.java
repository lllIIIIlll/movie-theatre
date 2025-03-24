package net.ow.movie.theatre.mapper.movie;

import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.movie.TMDBBaseMovie;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseMovieDTOMapper {
    @Mapping(target = "name", source = "title")
    BaseMovieDTO from(TMDBBaseMovie tmdbBaseMovie);

    @Mapping(target = "data", source = "results")
    @Mapping(target = "total", source = "totalResults")
    PaginatedResponse<BaseMovieDTO> from(
            TMDBPaginatedResponse<TMDBBaseMovie> tmdbPaginatedResponse);
}
