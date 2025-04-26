package net.ow.movie.theatre.fixture;

import java.util.List;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.tv.BaseTVShowDTO;
import net.ow.movie.theatre.dto.tv.TVShowDTO;

public class MockTVShowDTO {
    public static TVShowDTO mock(
            Integer id, String name, List<GenreDTO> genres, List<BaseTVShowDTO> recommendations) {
        TVShowDTO tvShow = new TVShowDTO();

        tvShow.setId(id);
        tvShow.setName(name);
        tvShow.setGenres(genres);
        tvShow.setRecommendations(recommendations);

        return tvShow;
    }
}
