package net.ow.movie.theatre.mapper;

import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.mapper.search.SearchResultToSearchResultDTOMapper;
import net.ow.movie.tmdb.model.search.SearchResult;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = SearchResultToSearchResultDTOMapper.class)
public interface SearchResultPagedResponseToSearchResultDTOPagedResponseMapper
        extends PagedResponseMapper<SearchResult, SearchResultDTO> {}
