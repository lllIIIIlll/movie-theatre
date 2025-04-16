package net.ow.movie.theatre.dto.person;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CastDTO {
    private Integer id;

    private String name;

    private String profilePath;

    private String character;

    private Integer order;
}
