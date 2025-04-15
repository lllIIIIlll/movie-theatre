package net.ow.movie.theatre.mapper.movie;

import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.mapper.genre.GenreDTOMapper;
import net.ow.movie.tmdb.constant.TMDBMediaType;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.movie.TMDBBaseMovie;
import net.ow.movie.tmdb.model.trending.TMDBTrendingMovie;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {GenreDTOMapper.class})
public interface BaseMovieDTOMapper {
    @Mapping(target = "name", source = "title")
    @Mapping(target = "genres", source = "genreIds")
    @Mapping(target = "rating", source = "voteAverage")
    @Mapping(target = "mediaType", constant = TMDBMediaType.MOVIE)
    BaseMovieDTO fromTMDBBaseMovie(TMDBBaseMovie tmdbBaseMovie);

    @Mapping(target = "name", source = "title")
    @Mapping(target = "genres", source = "genreIds")
    @Mapping(target = "rating", source = "voteAverage")
    BaseMovieDTO fromTMDBTrendingMovie(TMDBTrendingMovie tmdbTrendingMovie);

    @Mapping(target = "data", source = "results")
    @Mapping(target = "total", source = "totalResults")
    PaginatedResponse<BaseMovieDTO> fromTMDBPaginatedTrendingMovies(
            TMDBPaginatedResponse<TMDBTrendingMovie> tmdbPaginatedResponse);

    @Mapping(target = "data", source = "results")
    @Mapping(target = "total", source = "totalResults")
    PaginatedResponse<BaseMovieDTO> fromTMDBPaginatedBaseMovies(
            TMDBPaginatedResponse<TMDBBaseMovie> tmdbPaginatedResponse);
}
