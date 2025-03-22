package net.ow.movie.theatre.dto.movie;

import java.util.List;
import lombok.Data;
import net.ow.movie.theatre.dto.genre.GenreDTO;

@Data
public class BaseMovieDTO {
    private Integer id;

    private String posterPath;

    private String name;

    private List<GenreDTO> genres;
}
