package net.ow.movie.theatre.dto.movie;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import net.ow.movie.theatre.dto.genre.GenreDTO;

@Data
@ToString
public class BaseMovieDTO {
    private Integer id;

    private String mediaType;

    private String posterPath;

    private String backdropPath;

    private String name;

    private List<GenreDTO> genres;

    private Instant releaseDate;

    private String overview;

    private BigDecimal rating;
}
