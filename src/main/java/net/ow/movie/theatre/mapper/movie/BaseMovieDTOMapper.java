package net.ow.movie.theatre.mapper.movie;

import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.mapper.genre.GenreDTOMapper;
import net.ow.movie.tmdb.model.movie.TMDBBaseMovie;
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
    BaseMovieDTO fromTMDBBaseMovie(TMDBBaseMovie tmdbBaseMovie);
}
