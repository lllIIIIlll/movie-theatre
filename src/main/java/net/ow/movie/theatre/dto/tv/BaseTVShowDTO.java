package net.ow.movie.theatre.dto.tv;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Data;
import net.ow.movie.theatre.dto.genre.GenreDTO;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseTVShowDTO {
    protected Integer id;

    protected String mediaType;

    protected String posterPath;

    protected String backdropPath;

    protected String name;

    protected List<GenreDTO> genres;

    protected Instant releaseDate;

    protected String overview;

    protected BigDecimal rating;
}
