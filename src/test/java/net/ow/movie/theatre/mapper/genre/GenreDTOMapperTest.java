package net.ow.movie.theatre.mapper.genre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.fixture.MockTMDBGenre;
import net.ow.movie.theatre.fixture.MockTMDBGenreList;
import net.ow.movie.tmdb.model.genre.TMDBGenre;
import net.ow.movie.tmdb.model.genre.TMDBGenreList;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class GenreDTOMapperTest {
    private final GenreDTOMapper genreDTOMapper = Mappers.getMapper(GenreDTOMapper.class);

    @Test
    void fromTest_whenValidTMDBGenreList_OK() {
        Integer genreId = 1;
        TMDBGenre tmdbGenre = MockTMDBGenre.mock(genreId);
        TMDBGenreList tmdbGenreList = MockTMDBGenreList.mock(List.of(tmdbGenre));

        List<GenreDTO> result = genreDTOMapper.from(tmdbGenreList);
        assertEquals(1, result.size(), "Expected one GenreDTO in the result");
        assertEquals(genreId, result.get(0).getId(), "Expected GenreDTO id to be 1");
        assertEquals("Action", result.get(0).getName(), "Expected GenreDTO name to be 'Action'");
    }

    @Test
    void fromTest_whenTMDBGenreListNull_thenReturnsEmptyList() {
        List<GenreDTO> result = genreDTOMapper.from((TMDBGenreList) null);

        assertTrue(result.isEmpty(), "Expected an empty list for null input");
    }

    @Test
    void fromTest_whenGenresListIsNull_thenReturnsEmptyList() {
        TMDBGenreList tmdbGenreList = MockTMDBGenreList.mock(null);

        List<GenreDTO> result = genreDTOMapper.from(tmdbGenreList);
        assertTrue(result.isEmpty(), "Expected an empty list for null genres list");
    }
}
