package net.ow.movie.theatre.fixture;

import java.util.List;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.tv.BaseTVShowDTO;

public class MockBaseTVShowDTO {
    public static BaseTVShowDTO mock(Integer id, String name, List<GenreDTO> genres) {
        BaseTVShowDTO baseTVShow = new BaseTVShowDTO();

        baseTVShow.setId(id);
        baseTVShow.setName(name);
        baseTVShow.setGenres(genres);

        return baseTVShow;
    }
}
