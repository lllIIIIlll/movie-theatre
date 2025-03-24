package net.ow.movie.theatre.dto.movie;

import java.util.List;
import lombok.Data;
import lombok.ToString;
import net.ow.movie.theatre.dto.genre.GenreDTO;

@Data
@ToString
public class BaseMovieDTO {
    private Integer id;

    private String posterPath;

    private String name;

    private List<GenreDTO> genres;
}
