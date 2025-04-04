package net.ow.movie.theatre.mapper.search;

import net.ow.movie.theatre.dto.search.MovieSearchResultDTO;
import net.ow.movie.theatre.dto.search.PersonSearchResultDTO;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.dto.search.TVShowSearchResultDTO;
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
        uses = {
            MovieSearchResultDTOMapper.class,
            TVShowSearchResultDTOMapper.class,
            PersonSearchResultDTOMapper.class
        })
public interface SearchResultDTOMapper {
    @SubclassMapping(target = MovieSearchResultDTO.class, source = TMDBMovieSearchResult.class)
    @SubclassMapping(target = TVShowSearchResultDTO.class, source = TMDBTVShowSearchResult.class)
    @SubclassMapping(target = PersonSearchResultDTO.class, source = TMDBPersonSearchResult.class)
    SearchResultDTO fromTMDBSearchResult(TMDBSearchResult tmdbSearchResult);
}
