package net.ow.movie.theatre.dto.tv;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseTVSeasonDTO {
    private Instant releaseDate;

    private Integer episodeCount;

    private Integer id;

    private String name;

    private String overview;

    private String posterPath;

    private Integer seasonNumber;

    private Integer rating;
}
