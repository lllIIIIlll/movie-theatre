package net.ow.movie.theatre.mapper.trending;

import net.ow.movie.theatre.dto.trending.TrendingDTO;
import net.ow.movie.theatre.dto.trending.TrendingMovieDTO;
import net.ow.movie.theatre.dto.trending.TrendingPersonDTO;
import net.ow.movie.theatre.dto.trending.TrendingTVShowDTO;
import net.ow.movie.tmdb.model.trending.TMDBTrending;
import net.ow.movie.tmdb.model.trending.TMDBTrendingMovie;
import net.ow.movie.tmdb.model.trending.TMDBTrendingPerson;
import net.ow.movie.tmdb.model.trending.TMDBTrendingTVShow;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
            TrendingMovieDTOMapper.class,
            TrendingTVShowDTOMapper.class,
            TrendingPersonDTOMapper.class
        })
public interface TrendingDTOMapper {
    @SubclassMapping(target = TrendingMovieDTO.class, source = TMDBTrendingMovie.class)
    @SubclassMapping(target = TrendingTVShowDTO.class, source = TMDBTrendingTVShow.class)
    @SubclassMapping(target = TrendingPersonDTO.class, source = TMDBTrendingPerson.class)
    TrendingDTO fromTMDBTrending(TMDBTrending tmdbTrending);
}
