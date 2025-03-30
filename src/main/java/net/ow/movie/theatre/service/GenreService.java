package net.ow.movie.theatre.service;

import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ow.movie.theatre.dto.genre.GenreDTO;
import net.ow.movie.theatre.mapper.genre.GenreDTOMapper;
import net.ow.movie.tmdb.feign.TMDBFeignClient;
import net.ow.movie.tmdb.model.genre.TMDBGenreList;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {
    private final TMDBFeignClient tmdbFeignClient;

    private final GenreDTOMapper genreDTOMapper;

    public List<GenreDTO> getAllGenres(String language) {
        log.debug("Fetching genres from TMDB");
        TMDBGenreList tmdbGenreList = tmdbFeignClient.getGenres(language);
        log.debug("Fetched genres from TMDB");

        return genreDTOMapper.fromTMDBGenreList(tmdbGenreList);
    }

    public Map<Integer, GenreDTO> getAllGenresAsMap(String language) {
        List<GenreDTO> genres = getAllGenres(language);
        return genres.stream()
                .collect(
                        Collectors.toMap(
                                GenreDTO::getId,
                                genre -> genre,
                                (existing, replacement) -> replacement));
    }

    public List<GenreDTO> findGenresByIds(
            List<Integer> genreIds, Map<Integer, GenreDTO> genreIdToGenreMap) {
        return genreIds.stream()
                .distinct()
                .map(genreIdToGenreMap::get)
                .filter(Objects::nonNull)
                .toList();
    }
}
