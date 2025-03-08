package net.ow.movie.theatre.mapper.search;

import net.ow.movie.theatre.dto.search.PersonSearchResultDTO;
import net.ow.movie.theatre.mapper.SingleDirectionalMapper;
import net.ow.movie.tmdb.model.search.PersonSearchResult;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonSearchResultToMovieSearchResultDTOMapper
        extends SingleDirectionalMapper<PersonSearchResult, PersonSearchResultDTO> {
    @Override
    @Mapping(target = "posterPath", source = "profilePath")
    PersonSearchResultDTO map(PersonSearchResult personSearchResult);
}
