package net.ow.movie.theatre.mapper.genre;

import java.util.Collections;
import java.util.List;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.tmdb.model.genre.TMDBGenre;
import net.ow.movie.tmdb.model.genre.TMDBGenreList;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenreDTOMapper {
    default List<GenreDTO> fromTMDBGenreList(TMDBGenreList tmdbGenreList) {
        if (null == tmdbGenreList) {
            return Collections.emptyList();
        }

        List<TMDBGenre> tmdbGenres = tmdbGenreList.getGenres();
        if (null == tmdbGenres) {
            return Collections.emptyList();
        }

        return fromTMDBGenres(tmdbGenres);
    }

    List<GenreDTO> fromTMDBGenres(List<TMDBGenre> tmdbGenres);

    default List<GenreDTO> fromGenreIds(List<Integer> genreIds) {
        if (null == genreIds) {
            return Collections.emptyList();
        }

        return genreIds.stream().map(GenreDTO::new).toList();
    }
}
