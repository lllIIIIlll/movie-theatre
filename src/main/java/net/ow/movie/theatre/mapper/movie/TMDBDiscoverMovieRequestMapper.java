package net.ow.movie.theatre.mapper.movie;

import net.ow.movie.theatre.dto.movie.DiscoverMovieRequest;
import net.ow.movie.tmdb.model.discover.TMDBDiscoverMovieRequest;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TMDBDiscoverMovieRequestMapper {
    TMDBDiscoverMovieRequest fromDiscoverMovieRequest(
            DiscoverMovieRequest discoverMovieRequest, String language, Integer page);
}
