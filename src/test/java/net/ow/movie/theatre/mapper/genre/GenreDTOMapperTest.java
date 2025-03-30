package net.ow.movie.theatre.mapper.genre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.fixture.MockGenreDTO;
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

        List<GenreDTO> result = genreDTOMapper.fromTMDBGenreList(tmdbGenreList);
        assertEquals(1, result.size(), "Expected one GenreDTO in the result");
        assertEquals(genreId, result.get(0).getId(), "Expected GenreDTO id to be 1");
    }

    @Test
    void fromTest_whenTMDBGenreListNull_thenReturnsEmptyList() {
        List<GenreDTO> result = genreDTOMapper.fromTMDBGenreList(null);

        assertTrue(result.isEmpty(), "Expected an empty list for null input");
    }

    @Test
    void fromTest_whenGenresListIsNull_thenReturnsEmptyList() {
        TMDBGenreList tmdbGenreList = MockTMDBGenreList.mock(null);

        List<GenreDTO> result = genreDTOMapper.fromTMDBGenreList(tmdbGenreList);
        assertTrue(result.isEmpty(), "Expected an empty list for null genres list");
    }

    @Test
    void fromGenreIdsTest_OK() {
        List<Integer> genreIds = List.of(1, 2, 3);
        List<GenreDTO> expected =
                List.of(
                        MockGenreDTO.mock(1, null),
                        MockGenreDTO.mock(2, null),
                        MockGenreDTO.mock(3, null));

        List<GenreDTO> result = genreDTOMapper.fromGenreIds(genreIds);

        assertEquals(expected, result, "Expected list of GenreDTOs to match input genre IDs");
    }

    @Test
    void fromGenreIdsTest_whenNullInput_thenReturnsEmptyList() {
        List<GenreDTO> result = genreDTOMapper.fromGenreIds(null);
        assertTrue(result.isEmpty(), "Expected an empty list for null input");
    }
}
