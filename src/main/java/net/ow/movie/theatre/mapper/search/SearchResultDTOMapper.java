package net.ow.movie.theatre.mapper.search;

import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.search.MovieSearchResultDTO;
import net.ow.movie.theatre.dto.search.PersonSearchResultDTO;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.dto.search.TVSearchResultDTO;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.search.TMDBMovieSearchResult;
import net.ow.movie.tmdb.model.search.TMDBPersonSearchResult;
import net.ow.movie.tmdb.model.search.TMDBSearchResult;
import net.ow.movie.tmdb.model.search.TMDBTVSearchResult;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
            MovieSearchResultDTOMapper.class,
            TVSearchResultDTOMapper.class,
            PersonSearchResultDTOMapper.class
        })
public interface SearchResultDTOMapper {
    @SubclassMapping(target = MovieSearchResultDTO.class, source = TMDBMovieSearchResult.class)
    @SubclassMapping(target = TVSearchResultDTO.class, source = TMDBTVSearchResult.class)
    @SubclassMapping(target = PersonSearchResultDTO.class, source = TMDBPersonSearchResult.class)
    SearchResultDTO fromTMDBSearchResult(TMDBSearchResult tmdbSearchResult);

    @Mapping(target = "data", source = "results")
    @Mapping(target = "total", source = "totalResults")
    PaginatedResponse<SearchResultDTO> fromTMDBPaginatedSearchResults(
            TMDBPaginatedResponse<TMDBSearchResult> tmdbPaginatedResponse);
}
