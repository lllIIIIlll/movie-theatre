package net.ow.movie.theatre.mapper.search;

import net.ow.movie.theatre.dto.search.PersonSearchResultDTO;
import net.ow.movie.tmdb.model.search.TMDBPersonSearchResult;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonSearchResultDTOMapper {
    PersonSearchResultDTO fromTMDBPersonSearchResult(TMDBPersonSearchResult tmdbPersonSearchResult);
}
