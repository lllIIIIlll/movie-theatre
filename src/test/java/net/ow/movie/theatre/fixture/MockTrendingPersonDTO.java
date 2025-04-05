package net.ow.movie.theatre.fixture;

import net.ow.movie.theatre.dto.trending.TrendingPersonDTO;

public class MockTrendingPersonDTO {
    public static TrendingPersonDTO mock(Integer id) {
        TrendingPersonDTO trendingPersonDTO = new TrendingPersonDTO();

        trendingPersonDTO.setId(id);

        return trendingPersonDTO;
    }
}
