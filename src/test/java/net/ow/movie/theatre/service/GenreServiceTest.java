package net.ow.movie.theatre.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import feign.FeignException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.fixture.MockGenreDTO;
import net.ow.movie.theatre.fixture.MockTMDBGenre;
import net.ow.movie.theatre.fixture.MockTMDBGenreList;
import net.ow.movie.theatre.mapper.genre.GenreDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.genre.TMDBGenre;
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

    @Test
    void getGenresTest_OK() {
        String language = "zh-CN";
        Integer genreId1 = 1;
        Integer genreId2 = 2;
        String genreName = "genre-name";

        TMDBGenre tmdbGenre1 = MockTMDBGenre.mock(genreId1, genreName);
        TMDBGenre tmdbGenre2 = MockTMDBGenre.mock(genreId2, genreName);

        TMDBGenreList tmdbMovieGenreList = MockTMDBGenreList.mock(List.of(tmdbGenre1));
        TMDBGenreList tmdbTVShowGenreList = MockTMDBGenreList.mock(List.of(tmdbGenre2));

        GenreDTO genre1 = MockGenreDTO.mock(genreId1, genreName);
        GenreDTO genre2 = MockGenreDTO.mock(genreId2, genreName);

        List<GenreDTO> movieGenres = List.of(genre1);
        List<GenreDTO> tvShowGenres = List.of(genre2);

        when(tmdbFeignClient.getMovieGenres(language)).thenReturn(tmdbMovieGenreList);
        when(tmdbFeignClient.getTVShowGenres(language)).thenReturn(tmdbTVShowGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbMovieGenreList)).thenReturn(movieGenres);
        when(genreDTOMapper.fromTMDBGenreList(tmdbTVShowGenreList)).thenReturn(tvShowGenres);

        List<GenreDTO> actualGenres = genreService.getGenres(language);

        assertEquals(2, actualGenres.size());
        assertTrue(actualGenres.contains(genre1));
        assertTrue(actualGenres.contains(genre2));
        verify(tmdbFeignClient, times(1)).getMovieGenres(language);
        verify(tmdbFeignClient, times(1)).getTVShowGenres(language);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbMovieGenreList);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbTVShowGenreList);
    }

    @Test
    void getGenresTest_whenWithDuplicatesGenre_thenReturnsUniqueGenres() {
        String language = "zh-CN";

        Integer genreId1 = 1;
        String genreName = "genre-name";

        TMDBGenre tmdbGenre1 = MockTMDBGenre.mock(genreId1, genreName);

        TMDBGenreList tmdbGenreList = MockTMDBGenreList.mock(List.of(tmdbGenre1));

        GenreDTO genre1 = MockGenreDTO.mock(genreId1, genreName);

        List<GenreDTO> movieGenres = List.of(genre1);
        List<GenreDTO> tvShowGenres = List.of(genre1);

        when(tmdbFeignClient.getMovieGenres(language)).thenReturn(tmdbGenreList);
        when(tmdbFeignClient.getTVShowGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(movieGenres);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(tvShowGenres);

        List<GenreDTO> actualGenres = genreService.getGenres(language);

        assertEquals(1, actualGenres.size());
        assertTrue(actualGenres.contains(genre1));
        verify(tmdbFeignClient, times(1)).getMovieGenres(language);
        verify(tmdbFeignClient, times(1)).getTVShowGenres(language);
        verify(genreDTOMapper, times(2)).fromTMDBGenreList(tmdbGenreList);
    }

    @Test
    void getGenresTest_whenEmptyResponse_thenReturnsEmptyList() {
        String language = "zh-CN";

        TMDBGenreList tmdbGenreList = MockTMDBGenreList.mock();

        when(tmdbFeignClient.getMovieGenres(language)).thenReturn(tmdbGenreList);
        when(tmdbFeignClient.getTVShowGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(Collections.emptyList());

        List<GenreDTO> actualGenres = genreService.getGenres(language);

        assertTrue(actualGenres.isEmpty());
        verify(tmdbFeignClient, times(1)).getMovieGenres(language);
        verify(tmdbFeignClient, times(1)).getTVShowGenres(language);
        verify(genreDTOMapper, times(2)).fromTMDBGenreList(tmdbGenreList);
    }

    @Test
    void getGenresTest_whenThrowFeignExceptionException_thenThrowsException() {
        String language = "zh-CN";

        when(tmdbFeignClient.getMovieGenres(language)).thenThrow(FeignException.class);

        assertThrows(FeignException.class, () -> genreService.getGenres(language));

        verify(tmdbFeignClient, times(1)).getMovieGenres(language);
        verify(tmdbFeignClient, never()).getTVShowGenres(any());
        verify(genreDTOMapper, never()).fromTMDBGenreList(any());
    }

    @Test
    void getGenresAsMapTest_OK() {
        String language = "zh-CN";
        Integer genreId1 = 1;
        Integer genreId2 = 2;
        String genreName = "genre-name";

        GenreDTO genre1 = MockGenreDTO.mock(genreId1, genreName);
        GenreDTO genre2 = MockGenreDTO.mock(genreId2, genreName);

        List<GenreDTO> genres = List.of(genre1, genre2);

        when(genreService.getGenres(language)).thenReturn(genres);

        Map<Integer, GenreDTO> actualGenresMap = genreService.getGenresAsMap(language);

        assertEquals(2, actualGenresMap.size());
        assertTrue(actualGenresMap.containsKey(genreId1));
        assertTrue(actualGenresMap.containsKey(genreId2));
        assertEquals(genre1, actualGenresMap.get(genreId1));
        assertEquals(genre2, actualGenresMap.get(genreId2));
    }

    @Test
    void getGenresAsMapTest_whenEmptyList_thenReturnsEmptyMap() {
        String language = "zh-CN";

        when(genreService.getGenres(language)).thenReturn(Collections.emptyList());

        Map<Integer, GenreDTO> actualGenresMap = genreService.getGenresAsMap(language);

        assertTrue(actualGenresMap.isEmpty());
    }

    @Test
    void getGenresAsMapTest_whenDuplicateIds_thenReturnsMapWithUniqueGenre() {
        String language = "zh-CN";
        Integer genreId1 = 1;
        String genreName = "genre-name";

        GenreDTO genre1 = MockGenreDTO.mock(genreId1, genreName);
        GenreDTO genre2 = MockGenreDTO.mock(genreId1, "different-name");

        List<GenreDTO> genres = List.of(genre1, genre2);

        when(genreService.getGenres(language)).thenReturn(genres);

        Map<Integer, GenreDTO> actualGenresMap = genreService.getGenresAsMap(language);

        assertEquals(1, actualGenresMap.size());
        assertTrue(actualGenresMap.containsKey(genreId1));
        assertEquals(genre2, actualGenresMap.get(genreId1));
    }

    @Test
    void getMovieGenresTest_OK() {
        String language = "zh-CN";
        Integer genreId1 = 1;
        Integer genreId2 = 2;
        String genreName = "genre-name";

        TMDBGenre tmdbGenre1 = MockTMDBGenre.mock(genreId1, genreName);
        TMDBGenre tmdbGenre2 = MockTMDBGenre.mock(genreId2, genreName);

        TMDBGenreList tmdbGenreList = MockTMDBGenreList.mock(List.of(tmdbGenre1, tmdbGenre2));

        GenreDTO genre1 = MockGenreDTO.mock(genreId1, genreName);
        GenreDTO genre2 = MockGenreDTO.mock(genreId2, genreName);

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

        TMDBGenreList tmdbGenreList = MockTMDBGenreList.mock();

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
    void getMovieGenresAsMapTest_OK() {
        String language = "zh-CN";
        Integer genreId1 = 1;
        Integer genreId2 = 2;
        String genreName = "genre-name";

        GenreDTO genre1 = MockGenreDTO.mock(genreId1, genreName);
        GenreDTO genre2 = MockGenreDTO.mock(genreId2, genreName);

        List<GenreDTO> genres = List.of(genre1, genre2);

        when(genreService.getMovieGenres(language)).thenReturn(genres);

        Map<Integer, GenreDTO> actualGenresMap = genreService.getMovieGenresAsMap(language);

        assertEquals(2, actualGenresMap.size());
        assertTrue(actualGenresMap.containsKey(genreId1));
        assertTrue(actualGenresMap.containsKey(genreId2));
        assertEquals(genre1, actualGenresMap.get(genreId1));
        assertEquals(genre2, actualGenresMap.get(genreId2));
    }

    @Test
    void getMovieGenresAsMapTest_whenEmptyList_thenReturnsEmptyMap() {
        String language = "zh-CN";

        TMDBGenreList tmdbGenreList = MockTMDBGenreList.mock();

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

        Integer genreId1 = 1;
        String genreName = "genre-name";

        TMDBGenre tmdbGenre1 = MockTMDBGenre.mock(genreId1, genreName);

        TMDBGenreList tmdbGenreList = MockTMDBGenreList.mock(List.of(tmdbGenre1, tmdbGenre1));

        GenreDTO genre1 = MockGenreDTO.mock(genreId1, genreName);

        List<GenreDTO> genres = List.of(genre1, genre1);

        when(tmdbFeignClient.getMovieGenres(language)).thenReturn(tmdbGenreList);
        when(genreDTOMapper.fromTMDBGenreList(tmdbGenreList)).thenReturn(genres);

        Map<Integer, GenreDTO> genreMap = genreService.getMovieGenresAsMap(language);

        assertEquals(1, genreMap.size());
        assertTrue(genreMap.containsKey(1));
        assertEquals(genre1, genreMap.get(1));
        verify(tmdbFeignClient, times(1)).getMovieGenres(language);
        verify(genreDTOMapper, times(1)).fromTMDBGenreList(tmdbGenreList);
    }

    @Test
    void getTVShowGenresTest_OK() {
        String language = "zh-CN";
        Integer genreId1 = 1;
        Integer genreId2 = 2;
        String genreName = "genre-name";

        TMDBGenre tmdbGenre1 = MockTMDBGenre.mock(genreId1, genreName);
        TMDBGenre tmdbGenre2 = MockTMDBGenre.mock(genreId2, genreName);

        TMDBGenreList tmdbGenreList = MockTMDBGenreList.mock(List.of(tmdbGenre1, tmdbGenre2));

        GenreDTO genre1 = MockGenreDTO.mock(genreId1, genreName);
        GenreDTO genre2 = MockGenreDTO.mock(genreId2, genreName);

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

        TMDBGenreList tmdbGenreList = MockTMDBGenreList.mock();

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
    void getTVShowGenresAsMapTest_OK() {
        String language = "zh-CN";
        Integer genreId1 = 1;
        Integer genreId2 = 2;
        String genreName = "genre-name";

        GenreDTO genre1 = MockGenreDTO.mock(genreId1, genreName);
        GenreDTO genre2 = MockGenreDTO.mock(genreId2, genreName);

        List<GenreDTO> genres = List.of(genre1, genre2);

        when(genreService.getTVShowGenres(language)).thenReturn(genres);

        Map<Integer, GenreDTO> actualGenresMap = genreService.getTVShowGenresAsMap(language);

        assertEquals(2, actualGenresMap.size());
        assertTrue(actualGenresMap.containsKey(genreId1));
        assertTrue(actualGenresMap.containsKey(genreId2));
        assertEquals(genre1, actualGenresMap.get(genreId1));
        assertEquals(genre2, actualGenresMap.get(genreId2));
    }

    @Test
    void getTVShowGenresAsMapTest_whenEmptyList_thenReturnsEmptyMap() {
        String language = "zh-CN";

        TMDBGenreList tmdbGenreList = MockTMDBGenreList.mock();

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
        Integer genreId1 = 1;
        String genreName = "genre-name";

        TMDBGenre tmdbGenre1 = MockTMDBGenre.mock(genreId1, genreName);

        TMDBGenreList tmdbGenreList = MockTMDBGenreList.mock(List.of(tmdbGenre1, tmdbGenre1));

        GenreDTO genre1 = MockGenreDTO.mock(genreId1, genreName);
        GenreDTO genre2 = MockGenreDTO.mock(genreId1, genreName);

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
