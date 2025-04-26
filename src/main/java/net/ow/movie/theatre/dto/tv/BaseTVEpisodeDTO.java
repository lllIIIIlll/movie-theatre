package net.ow.movie.theatre.dto.tv;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;

@Data
public class BaseTVEpisodeDTO {
    private Instant airDate;

    private Integer episodeNumber;

    private String name;

    private Integer id;

    private String overview;

    private Integer seasonNumber;

    private String posterPath;

    private BigDecimal rating;
}
