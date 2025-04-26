package net.ow.movie.theatre.dto.tv;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseTVSeasonDTO {
    protected Instant releaseDate;

    protected Integer episodeCount;

    protected Integer id;

    protected String name;

    protected String overview;

    protected String posterPath;

    protected Integer seasonNumber;

    protected Integer rating;
}
