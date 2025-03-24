package net.ow.movie.theatre.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.mapper.genre.GenreDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.genre.TMDBGenreList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {
    @InjectMocks private GenreService genreService;

    @Mock private TMDBFeignClient tmdbFeignClient;

    @Mock private GenreDTOMapper genreDTOMapper;

    @Mock private TMDBGenreList tmdbGenreList;

    @Mock private List<GenreDTO> genres;

    @Mock private GenreDTO genre1;

    @Mock private GenreDTO genre2;

    @Test
    void getGenresTest_OK() {
        String language = "zh-CN";

        when(tmdbFeignClient.getGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.from(tmdbGenreList)).thenReturn(genres);

        List<GenreDTO> actualGenres = genreService.getGenres(language);

        assertEquals(genres, actualGenres);
        verify(tmdbFeignClient, times(1)).getGenres(language);
        verify(genreDTOMapper, times(1)).from(tmdbGenreList);
    }

    @Test
    void getGenresByIds_OK() {
        String language = "zh-CN";

        when(genre1.getId()).thenReturn(1);
        when(genre2.getId()).thenReturn(2);
        List<GenreDTO> genres = List.of(genre1, genre2);

        when(tmdbFeignClient.getGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.from(tmdbGenreList)).thenReturn(genres);

        List<GenreDTO> actualGenres = genreService.getGenresByIds(List.of(1, 2), language);

        assertEquals(2, actualGenres.size());
        assertTrue(actualGenres.containsAll(genres));
        verify(tmdbFeignClient, times(1)).getGenres(language);
        verify(genreDTOMapper, times(1)).from(tmdbGenreList);
    }

    @Test
    void getGenresByIdsTest_whenEmptyIdsList_thenReturnsEmptyList() {
        String language = "zh-CN";

        when(tmdbFeignClient.getGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.from(tmdbGenreList)).thenReturn(genres);

        List<GenreDTO> actualGenres =
                genreService.getGenresByIds(Collections.emptyList(), language);

        assertTrue(actualGenres.isEmpty());
        verify(tmdbFeignClient, times(1)).getGenres(language);
        verify(genreDTOMapper, times(1)).from(tmdbGenreList);
    }

    @Test
    void getGenresByIdsTest_whenInvalidIds_thenReturnsEmptyList() {
        String language = "zh-CN";

        when(genre1.getId()).thenReturn(1);
        when(genre2.getId()).thenReturn(2);
        List<GenreDTO> genres = List.of(genre1, genre2);

        when(tmdbFeignClient.getGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.from(tmdbGenreList)).thenReturn(genres);

        List<GenreDTO> actualGenres = genreService.getGenresByIds(List.of(3, 4), language);

        assertTrue(actualGenres.isEmpty());
        verify(tmdbFeignClient, times(1)).getGenres(language);
        verify(genreDTOMapper, times(1)).from(tmdbGenreList);
    }

    @Test
    void getGenresByIdsTest_whenDuplicateIds_thenReturnsUniqueGenres() {
        String language = "zh-CN";

        when(genre1.getId()).thenReturn(1);
        when(genre2.getId()).thenReturn(2);
        List<GenreDTO> genres = List.of(genre1, genre2);

        when(tmdbFeignClient.getGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.from(tmdbGenreList)).thenReturn(genres);

        List<GenreDTO> actualGenres = genreService.getGenresByIds(List.of(1, 1, 2), language);

        assertEquals(2, actualGenres.size());
        assertTrue(actualGenres.containsAll(genres));
        verify(tmdbFeignClient, times(1)).getGenres(language);
        verify(genreDTOMapper, times(1)).from(tmdbGenreList);
    }
}
