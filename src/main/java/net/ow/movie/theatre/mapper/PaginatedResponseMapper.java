package net.ow.movie.theatre.mapper;

import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.dto.trending.TrendingMovieDTO;
import net.ow.movie.theatre.dto.trending.TrendingTVShowDTO;
import net.ow.movie.theatre.mapper.movie.BaseMovieDTOMapper;
import net.ow.movie.theatre.mapper.movie.TrendingMovieDTOMapper;
import net.ow.movie.theatre.mapper.search.SearchResultDTOMapper;
import net.ow.movie.theatre.mapper.tv.TrendingTVShowDTOMapper;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.movie.TMDBBaseMovie;
import net.ow.movie.tmdb.model.search.TMDBSearchResult;
import net.ow.movie.tmdb.model.trending.TMDBTrendingMovie;
import net.ow.movie.tmdb.model.trending.TMDBTrendingTVShow;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
            SearchResultDTOMapper.class,
            BaseMovieDTOMapper.class,
            TrendingMovieDTOMapper.class,
            TrendingTVShowDTOMapper.class
        })
public interface PaginatedResponseMapper {
    @Mapping(target = "data", source = "results")
    @Mapping(target = "total", source = "totalResults")
    PaginatedResponse<SearchResultDTO> fromTMDBPaginatedSearchResults(
            TMDBPaginatedResponse<TMDBSearchResult> tmdbPaginatedResponse);

    @Mapping(target = "data", source = "results")
    @Mapping(target = "total", source = "totalResults")
    PaginatedResponse<BaseMovieDTO> fromTMDBPaginatedBaseMovies(
            TMDBPaginatedResponse<TMDBBaseMovie> tmdbPaginatedResponse);

    @Mapping(target = "data", source = "results")
    @Mapping(target = "total", source = "totalResults")
    PaginatedResponse<TrendingMovieDTO> fromTMDBPaginatedTrendingMovies(
            TMDBPaginatedResponse<TMDBTrendingMovie> tmdbPaginatedResponse);

    @Mapping(target = "data", source = "results")
    @Mapping(target = "total", source = "totalResults")
    PaginatedResponse<TrendingTVShowDTO> fromTMDBPaginatedTrendingTVShows(
            TMDBPaginatedResponse<TMDBTrendingTVShow> tmdbPaginatedResponse);
}
