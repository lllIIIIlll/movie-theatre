package net.ow.movie.theatre.mapper.search;

import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.mapper.genre.GenreDTOMapper;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.search.TMDBMovieSearchResult;
import net.ow.movie.tmdb.model.search.TMDBPersonSearchResult;
import net.ow.movie.tmdb.model.search.TMDBSearchResult;
import net.ow.movie.tmdb.model.search.TMDBTVShowSearchResult;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = GenreDTOMapper.class)
public interface SearchResultDTOMapper {
    @SubclassMapping(target = SearchResultDTO.class, source = TMDBMovieSearchResult.class)
    @SubclassMapping(target = SearchResultDTO.class, source = TMDBTVShowSearchResult.class)
    @SubclassMapping(target = SearchResultDTO.class, source = TMDBPersonSearchResult.class)
    SearchResultDTO fromTMDBSearchResult(TMDBSearchResult tmdbSearchResult);

    @Mapping(target = "name", source = "title")
    @Mapping(target = "genres", source = "genreIds")
    SearchResultDTO fromTMDBMovieSearchResult(TMDBMovieSearchResult tmdbMovieSearchResult);

    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "releaseDate", ignore = true)
    @Mapping(target = "posterPath", source = "profilePath")
    SearchResultDTO fromTMDBPersonSearchResult(TMDBPersonSearchResult tmdbPersonSearchResult);

    @Mapping(target = "genres", source = "genreIds")
    @Mapping(target = "releaseDate", source = "firstAirDate")
    SearchResultDTO fromTMDBTVShowSearchResult(TMDBTVShowSearchResult tmdbtvShowSearchResult);

    @Mapping(target = "data", source = "results")
    @Mapping(target = "total", source = "totalResults")
    PaginatedResponse<SearchResultDTO> fromTMDBPaginatedSearchResults(
            TMDBPaginatedResponse<TMDBSearchResult> tmdbPaginatedResponse);
}
