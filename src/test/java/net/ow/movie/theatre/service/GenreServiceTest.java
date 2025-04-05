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

    @Test
    void ggetMovieGenresAsMapTest_whenEmptyList_thenReturnsEmptyMap() {
        String language = "zh-CN";

        when(tmdbFeignClient.getMovieGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(Collections.emptyList());

        Map<Integer, GenreDTO> genreMap = genreService.getMovieGenresAsMap(language);

        assertTrue(genreMap.isEmpty());
        verify(tmdbFeignClient, times(1)).getMovieGenres(language);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbGenreList);
    }

    @Test
    void getMovieGenresAsMapTest_whenDuplicateIds_thenReturnsUniqueGenres() {
        String language = "zh-CN";

        Integer genreId = 1;
        GenreDTO genre1 = MockGenreDTO.mock(genreId);
        GenreDTO genre2 = MockGenreDTO.mock(genreId);

        List<GenreDTO> genres = List.of(genre1, genre2);

        when(tmdbFeignClient.getMovieGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(genres);

        Map<Integer, GenreDTO> genreMap = genreService.getMovieGenresAsMap(language);

        assertEquals(1, genreMap.size());
        assertTrue(genreMap.containsKey(1));
        assertEquals(genre2, genreMap.get(1));
        verify(tmdbFeignClient, times(1)).getMovieGenres(language);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbGenreList);
    }

    @Test
    void getTVShowGenresTest_OK() {
        String language = "zh-CN";

        GenreDTO genre1 = MockGenreDTO.mock();
        GenreDTO genre2 = MockGenreDTO.mock();
        List<GenreDTO> genres = List.of(genre1, genre2);

        when(tmdbFeignClient.getTVShowGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(genres);

        List<GenreDTO> actualGenres = genreService.getTVShowGenres(language);

        assertEquals(genres, actualGenres);
        verify(tmdbFeignClient, times(1)).getTVShowGenres(language);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbGenreList);
    }

    @Test
    void getTVShowGenresTest_whenEmptyResponse_thenReturnsEmptyList() {
        String language = "zh-CN";

        when(tmdbFeignClient.getTVShowGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(Collections.emptyList());

        List<GenreDTO> actualGenres = genreService.getTVShowGenres(language);

        assertTrue(actualGenres.isEmpty());
        verify(tmdbFeignClient, times(1)).getTVShowGenres(language);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbGenreList);
    }

    @Test
    void getTVShowGenresTest_whenThrowFeignExceptionException_thenThrowsException() {
        String language = "zh-CN";

        when(tmdbFeignClient.getTVShowGenres(language)).thenThrow(FeignException.class);

        assertThrows(FeignException.class, () -> genreService.getTVShowGenres(language));
        verify(tmdbFeignClient, times(1)).getTVShowGenres(language);
        verify(genreDTOMapper, never()).fromTMDBGenreList(any());
    }

    @Test
    void getTVShowGenresAsMapTest_whenEmptyList_thenReturnsEmptyMap() {
        String language = "zh-CN";

        when(tmdbFeignClient.getTVShowGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(Collections.emptyList());

        Map<Integer, GenreDTO> genreMap = genreService.getTVShowGenresAsMap(language);

        assertTrue(genreMap.isEmpty());
        verify(tmdbFeignClient, times(1)).getTVShowGenres(language);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbGenreList);
    }

    @Test
    void getTVShowGenresAsMapTest_whenDuplicateIds_thenReturnsUniqueGenres() {
        String language = "zh-CN";

        Integer genreId = 1;
        GenreDTO genre1 = MockGenreDTO.mock(genreId);
        GenreDTO genre2 = MockGenreDTO.mock(genreId);

        List<GenreDTO> genres = List.of(genre1, genre2);

        when(tmdbFeignClient.getTVShowGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(genres);

        Map<Integer, GenreDTO> genreMap = genreService.getTVShowGenresAsMap(language);

        assertEquals(1, genreMap.size());
        assertTrue(genreMap.containsKey(1));
        assertEquals(genre2, genreMap.get(1));
        verify(tmdbFeignClient, times(1)).getTVShowGenres(language);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbGenreList);
    }
}
