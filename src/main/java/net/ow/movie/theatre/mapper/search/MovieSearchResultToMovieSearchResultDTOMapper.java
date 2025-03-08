package net.ow.movie.theatre.mapper.search;

import net.ow.movie.theatre.dto.search.MovieSearchResultDTO;
import net.ow.movie.theatre.mapper.SingleDirectionalMapper;
import net.ow.movie.tmdb.model.search.MovieSearchResult;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovieSearchResultToMovieSearchResultDTOMapper
        extends SingleDirectionalMapper<MovieSearchResult, MovieSearchResultDTO> {
    @Override
    @Mapping(target = "name", source = "title")
    MovieSearchResultDTO map(MovieSearchResult movieSearchResult);
}
