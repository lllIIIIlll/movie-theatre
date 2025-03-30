package net.ow.movie.theatre.dto.movie;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import net.ow.movie.theatre.dto.genre.GenreDTO;

@Data
@ToString
public class BaseMovieDTO {
    private Integer id;

    private String posterPath;

    private String name;

    private List<GenreDTO> genres;

    private Instant releaseDate;

    private String overview;

    public void setGenres(@NonNull Map<Integer, GenreDTO> genreIdToGenreMap) {
        List<GenreDTO> genres = getGenres();
        if (null == genres) {
            return;
        }

        List<Integer> genreIds = genres.stream().map(GenreDTO::getId).toList();
        this.genres = genreIds.stream().map(genreIdToGenreMap::get).toList();
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }
}
