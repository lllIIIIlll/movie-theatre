package net.ow.movie.theatre.mapper.trending;

import net.ow.movie.theatre.dto.trending.TrendingMovieDTO;
import net.ow.movie.theatre.mapper.genre.GenreDTOMapper;
import net.ow.movie.tmdb.model.trending.TMDBTrendingMovie;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {GenreDTOMapper.class})
public interface TrendingMovieDTOMapper {
    @Mapping(target = "name", source = "title")
    @Mapping(target = "genres", source = "genreIds")
    @Mapping(target = "rating", source = "voteAverage")
    TrendingMovieDTO fromTMDBTrendingMovie(TMDBTrendingMovie tmdbTrendingMovie);
}
