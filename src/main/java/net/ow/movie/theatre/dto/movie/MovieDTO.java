package net.ow.movie.theatre.dto.movie;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.ow.movie.theatre.dto.person.CastDTO;
import net.ow.movie.theatre.dto.person.CrewDTO;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDTO extends BaseMovieDTO {
    private List<CastDTO> cast;

    private CrewDTO director;

    private CrewDTO editor;

    private List<BaseMovieDTO> recommendations;
}
