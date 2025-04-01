package net.ow.movie.theatre.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import feign.FeignException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.fixture.MockGenreDTO;
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

    @Mock private GenreDTO genre1;

    @Mock private GenreDTO genre2;

    @Test
    void getAllGenresTest_OK() {
        String language = "zh-CN";

        List<GenreDTO> genres = List.of(genre1, genre2);

        when(tmdbFeignClient.getGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(genres);

        List<GenreDTO> actualGenres = genreService.getAllGenres(language);

        assertEquals(genres, actualGenres);
        verify(tmdbFeignClient, times(1)).getGenres(language);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbGenreList);
    }

    @Test
    void getAllGenresAsMapTest_OK() {
        String language = "zh-CN";

        when(genre1.getId()).thenReturn(1);
        when(genre2.getId()).thenReturn(2);
        List<GenreDTO> genres = List.of(genre1, genre2);

        when(tmdbFeignClient.getGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(genres);

        Map<Integer, GenreDTO> genreMap = genreService.getAllGenresAsMap(language);

        assertEquals(2, genreMap.size());
        assertTrue(genreMap.containsKey(1));
        assertTrue(genreMap.containsKey(2));
        assertEquals(genre1, genreMap.get(1));
        assertEquals(genre2, genreMap.get(2));
        verify(tmdbFeignClient, times(1)).getGenres(language);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbGenreList);
    }

    @Test
    void getAllGenresAsMapTest_whenEmptyList_thenReturnsEmptyMap() {
        String language = "zh-CN";

        when(tmdbFeignClient.getGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(Collections.emptyList());

        Map<Integer, GenreDTO> genreMap = genreService.getAllGenresAsMap(language);

        assertTrue(genreMap.isEmpty());
        verify(tmdbFeignClient, times(1)).getGenres(language);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbGenreList);
    }

    @Test
    void getAllGenresAsMapTest_whenDuplicateIds_thenReturnsUniqueGenres() {
        String language = "zh-CN";

        when(genre1.getId()).thenReturn(1);
        when(genre2.getId()).thenReturn(1);
        List<GenreDTO> genres = List.of(genre1, genre2);

        when(tmdbFeignClient.getGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(genres);

        Map<Integer, GenreDTO> genreMap = genreService.getAllGenresAsMap(language);

        assertEquals(1, genreMap.size());
        assertTrue(genreMap.containsKey(1));
        assertEquals(genre2, genreMap.get(1));
        verify(tmdbFeignClient, times(1)).getGenres(language);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbGenreList);
    }

    @Test
    void getMovieGenresTest_OK() {
        String language = "zh-CN";

        GenreDTO genre1 = MockGenreDTO.mock(1, "genre-name");
        GenreDTO genre2 = MockGenreDTO.mock(2, "genre-name");

        List<GenreDTO> genres = List.of(genre1, genre2);

        when(tmdbFeignClient.getMovieGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(genres);

        List<GenreDTO> actualGenres = genreService.getMovieGenres(language);

        assertEquals(genres, actualGenres);
        verify(tmdbFeignClient, times(1)).getMovieGenres(language);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbGenreList);
    }

    @Test
    void getMovieGenresTest_whenEmptyResponse_thenReturnsEmptyList() {
        String language = "zh-CN";

        when(tmdbFeignClient.getMovieGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(Collections.emptyList());

        List<GenreDTO> actualGenres = genreService.getMovieGenres(language);

        assertTrue(actualGenres.isEmpty());
        verify(tmdbFeignClient, times(1)).getMovieGenres(language);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbGenreList);
    }

    @Test
    void getMovieGenresTest_whenThrowFeignExceptionException_thenThrowsException() {
        String language = "zh-CN";

        when(tmdbFeignClient.getMovieGenres(language)).thenThrow(FeignException.class);

        assertThrows(FeignException.class, () -> genreService.getMovieGenres(language));

        verify(tmdbFeignClient, times(1)).getMovieGenres(language);
        verify(genreDTOMapper, never()).fromTMDBGenreList(any());
    }
}
