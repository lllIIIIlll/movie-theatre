package net.ow.movie.theatre.dto.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TVShowSearchResultDTO extends SearchResultDTO {
    private Integer id;

    private String mediaType;

    private String name;

    private String overview;

    private Instant releaseDate;

    private String posterPath;
}
