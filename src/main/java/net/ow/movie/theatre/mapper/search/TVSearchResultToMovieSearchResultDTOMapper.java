package net.ow.movie.theatre.mapper.search;

import net.ow.movie.theatre.dto.search.TVSearchResultDTO;
import net.ow.movie.theatre.mapper.SingleDirectionalMapper;
import net.ow.movie.tmdb.model.search.TVSearchResult;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TVSearchResultToMovieSearchResultDTOMapper
        extends SingleDirectionalMapper<TVSearchResult, TVSearchResultDTO> {
    @Override
    @Mapping(target = "releaseDate", source = "firstAirDate")
    TVSearchResultDTO map(TVSearchResult tvSearchResult);
}
