package net.ow.movie.theatre.mapper.search;

import net.ow.movie.theatre.dto.search.MovieSearchResultDTO;
import net.ow.movie.theatre.dto.search.PersonSearchResultDTO;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.dto.search.TVSearchResultDTO;
import net.ow.movie.theatre.mapper.SingleDirectionalMapper;
import net.ow.movie.tmdb.model.search.MovieSearchResult;
import net.ow.movie.tmdb.model.search.PersonSearchResult;
import net.ow.movie.tmdb.model.search.SearchResult;
import net.ow.movie.tmdb.model.search.TVSearchResult;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
            MovieSearchResultToMovieSearchResultDTOMapper.class,
            TVSearchResultToMovieSearchResultDTOMapper.class,
            PersonSearchResultToMovieSearchResultDTOMapper.class
        })
public interface SearchResultToSearchResultDTOMapper
        extends SingleDirectionalMapper<SearchResult, SearchResultDTO> {
    @Override
    @SubclassMappings({
        @SubclassMapping(target = MovieSearchResultDTO.class, source = MovieSearchResult.class),
        @SubclassMapping(target = TVSearchResultDTO.class, source = TVSearchResult.class),
        @SubclassMapping(target = PersonSearchResultDTO.class, source = PersonSearchResult.class)
    })
    SearchResultDTO map(SearchResult searchResult);
}
