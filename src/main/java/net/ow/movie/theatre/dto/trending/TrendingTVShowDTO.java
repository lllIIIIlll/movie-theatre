package net.ow.movie.theatre.dto.trending;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.ow.movie.theatre.dto.genre.GenreDTO;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrendingTVShowDTO extends TrendingDTO {
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
