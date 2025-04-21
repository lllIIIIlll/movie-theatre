package net.ow.movie.theatre.dto.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;
import lombok.Data;
import net.ow.movie.theatre.dto.genre.GenreDTO;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResultDTO {
    private Integer id;

    private String mediaType;

    private String name;

    private String overview;

    private Instant releaseDate;

    private String posterPath;

    private List<GenreDTO> genres;
}
