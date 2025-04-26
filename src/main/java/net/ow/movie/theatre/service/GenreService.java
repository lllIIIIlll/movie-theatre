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

    public List<GenreDTO> getGenres(String language) {
        List<GenreDTO> movieGenres = getMovieGenres(language);
        List<GenreDTO> tvShowGenres = getTVShowGenres(language);

        Set<GenreDTO> genres = new HashSet<>();
        genres.addAll(movieGenres);
        genres.addAll(tvShowGenres);

        return new ArrayList<>(genres);
    }

    public Map<Integer, GenreDTO> getGenresAsMap(String language) {
        List<GenreDTO> genres = getGenres(language);
        return convertGenresToMap(genres);
    }

    public List<GenreDTO> getMovieGenres(String language) {
        TMDBGenreList tmdbGenreList = tmdbFeignClient.getMovieGenres(language);
        return genreDTOMapper.fromTMDBGenreList(tmdbGenreList);
    }

    public Map<Integer, GenreDTO> getMovieGenresAsMap(String language) {
        List<GenreDTO> genres = getMovieGenres(language);
        return convertGenresToMap(genres);
    }

    public List<GenreDTO> getTVShowGenres(String language) {
        TMDBGenreList tmdbGenreList = tmdbFeignClient.getTVShowGenres(language);
        return genreDTOMapper.fromTMDBGenreList(tmdbGenreList);
    }

    public Map<Integer, GenreDTO> getTVShowGenresAsMap(String language) {
        List<GenreDTO> genres = getTVShowGenres(language);
        return convertGenresToMap(genres);
    }

    private Map<Integer, GenreDTO> convertGenresToMap(List<GenreDTO> genres) {
        return genres.stream()
                .collect(
                        Collectors.toMap(
                                GenreDTO::getId,
                                genre -> genre,
                                (existing, replacement) -> replacement));
    }
}
