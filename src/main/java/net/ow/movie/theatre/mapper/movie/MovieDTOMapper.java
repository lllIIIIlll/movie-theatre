package net.ow.movie.theatre.mapper.movie;

import net.ow.movie.theatre.dto.movie.MovieDTO;
import net.ow.movie.theatre.mapper.genre.GenreDTOMapper;
import net.ow.movie.tmdb.constant.TMDBMediaType;
import net.ow.movie.tmdb.model.movie.TMDBMovie;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {GenreDTOMapper.class, BaseMovieDTOMapper.class})
public interface MovieDTOMapper {
    @Mapping(target = "name", source = "title")
    @Mapping(target = "rating", source = "voteAverage")
    @Mapping(target = "mediaType", constant = TMDBMediaType.MOVIE)
    MovieDTO fromTMDBMovie(TMDBMovie tmdbMovie);
}
