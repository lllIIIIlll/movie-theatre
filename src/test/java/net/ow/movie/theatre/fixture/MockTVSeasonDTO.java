package net.ow.movie.theatre.fixture;

import net.ow.movie.theatre.dto.tv.TVSeasonDTO;

public class MockTVSeasonDTO {
    public static TVSeasonDTO mock(Integer id, String name) {
        TVSeasonDTO tvSeason = new TVSeasonDTO();

        tvSeason.setId(id);
        tvSeason.setName(name);

        return tvSeason;
    }
}
