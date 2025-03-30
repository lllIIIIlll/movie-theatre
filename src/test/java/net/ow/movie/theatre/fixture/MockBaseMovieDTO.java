package net.ow.movie.theatre.fixture;

import java.util.List;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.movie.BaseMovieDTO;

public class MockBaseMovieDTO {
    public static BaseMovieDTO mock(Integer id, String name, List<GenreDTO> genres) {
        BaseMovieDTO baseMovieDTO = new BaseMovieDTO();

        baseMovieDTO.setId(id);
        baseMovieDTO.setName(name);
        baseMovieDTO.setGenres(genres);

        return baseMovieDTO;
    }

    public static BaseMovieDTO mock(Integer id, String name) {
        BaseMovieDTO baseMovieDTO = new BaseMovieDTO();

        baseMovieDTO.setId(id);
        baseMovieDTO.setName(name);

        return baseMovieDTO;
    }
}
