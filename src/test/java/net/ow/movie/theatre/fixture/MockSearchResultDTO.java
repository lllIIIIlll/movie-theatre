package net.ow.movie.theatre.fixture;

import java.util.List;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.search.SearchResultDTO;

public class MockSearchResultDTO {
    public static SearchResultDTO mock(Integer id, String name, List<GenreDTO> genres) {
        SearchResultDTO searchResultDTO = new SearchResultDTO();

        searchResultDTO.setId(id);
        searchResultDTO.setName(name);
        searchResultDTO.setGenres(genres);

        return searchResultDTO;
    }

    public static SearchResultDTO mock(Integer id, String name) {
        SearchResultDTO searchResultDTO = new SearchResultDTO();

        searchResultDTO.setId(id);
        searchResultDTO.setName(name);

        return searchResultDTO;
    }
}
