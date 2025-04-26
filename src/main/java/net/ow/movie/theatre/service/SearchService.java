package net.ow.movie.theatre.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ow.movie.theatre.constant.TMDBConstant;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.dto.pagination.PaginatedResponse;
import net.ow.movie.theatre.dto.search.SearchResultDTO;
import net.ow.movie.theatre.mapper.search.SearchResultDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.common.TMDBPaginatedResponse;
import net.ow.movie.tmdb.model.search.TMDBSearchResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {
    private final TMDBFeignClient tmdbFeignClient;

    private final GenreService genreService;

    private final SearchResultDTOMapper searchResultDTOMapper;

    public PaginatedResponse<SearchResultDTO> search(
            String query, Integer pageNumber, String language) {
        TMDBPaginatedResponse<TMDBSearchResult> tmdbPaginatedResponse =
                tmdbFeignClient.search(query, TMDBConstant.INCLUDE_ADULT, language, pageNumber);

        PaginatedResponse<SearchResultDTO> paginatedResponse =
                searchResultDTOMapper.fromTMDBPaginatedSearchResults(tmdbPaginatedResponse);

        // NOTE: When searching movies or tv shows from TMDB,
        // only ids are included in the response for genres.
        Map<Integer, GenreDTO> genreIdToGenreMap = genreService.getGenresAsMap(language);
        paginatedResponse
                .getData()
                .forEach(
                        searchResult -> enrichBaseMovieWithGenres(searchResult, genreIdToGenreMap));

        return paginatedResponse;
    }

    private void enrichBaseMovieWithGenres(
            SearchResultDTO searchResult, Map<Integer, GenreDTO> movieGenresMap) {
        if (null == searchResult.getGenres()) {
            return;
        }

        List<Integer> genreIds = searchResult.getGenres().stream().map(GenreDTO::getId).toList();
        List<GenreDTO> genres = genreIds.stream().map(movieGenresMap::get).toList();
        searchResult.setGenres(genres);
    }
}
