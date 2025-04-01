package net.ow.movie.theatre.fixture;

import net.ow.movie.theatre.dto.genre.GenreDTO;

public class MockGenreDTO {
    public static GenreDTO mock() {
        return new GenreDTO();
    }

    public static GenreDTO mock(Integer id) {
        GenreDTO genre = new GenreDTO();

        genre.setId(id);

        return genre;
    }

    public static GenreDTO mock(Integer id, String name) {
        GenreDTO genre = new GenreDTO();

        genre.setId(id);
        genre.setName(name);

        return genre;
    }
}
