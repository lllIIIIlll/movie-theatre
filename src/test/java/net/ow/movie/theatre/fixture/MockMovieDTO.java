package net.ow.movie.theatre.fixture;

import java.util.List;
import net.ow.movie.theatre.dto.movie.BaseMovieDTO;
import net.ow.movie.theatre.dto.movie.MovieDTO;

public class MockMovieDTO {
    public static MovieDTO mock(Integer id, String name, List<BaseMovieDTO> recommendations) {
        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(id);
        movieDTO.setName(name);
        movieDTO.setRecommendations(recommendations);

        return movieDTO;
    }
}
