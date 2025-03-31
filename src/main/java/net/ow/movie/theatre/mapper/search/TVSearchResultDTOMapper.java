package net.ow.movie.theatre.mapper.search;

import net.ow.movie.theatre.dto.search.TVSearchResultDTO;
import net.ow.movie.tmdb.model.search.TMDBTVSearchResult;
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
public interface TVSearchResultDTOMapper {
    TVSearchResultDTO fromTMDBTVSearchResult(TMDBTVSearchResult tmdbtvSearchResult);
}
