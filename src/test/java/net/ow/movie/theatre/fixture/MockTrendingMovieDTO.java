package net.ow.movie.theatre.fixture;

import java.util.List;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.trending.TrendingMovieDTO;

public class MockTrendingMovieDTO {
    public static TrendingMovieDTO mock(Integer id, String name, List<GenreDTO> genres) {
        TrendingMovieDTO trendingMovieDTO = new TrendingMovieDTO();

        trendingMovieDTO.setId(id);
        trendingMovieDTO.setName(name);
        trendingMovieDTO.setGenres(genres);

        return trendingMovieDTO;
    }
}
