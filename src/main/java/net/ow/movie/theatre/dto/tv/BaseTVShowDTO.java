package net.ow.movie.theatre.dto.tv;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Data;
import net.ow.movie.theatre.dto.genre.GenreDTO;

@Data
public class BaseTVShowDTO {
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
