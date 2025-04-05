package net.ow.movie.theatre.fixture;

import java.util.List;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.trending.TrendingTVShowDTO;

public class MockTrendingTVShowDTO {
    public static TrendingTVShowDTO mock(Integer id, String name, List<GenreDTO> genres) {
        TrendingTVShowDTO trendingTVShow = new TrendingTVShowDTO();

        trendingTVShow.setId(id);
        trendingTVShow.setName(name);
        trendingTVShow.setGenres(genres);

        return trendingTVShow;
    }
}
