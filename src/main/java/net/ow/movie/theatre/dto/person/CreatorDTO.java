package net.ow.movie.theatre.dto.person;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CreatorDTO {
    private Integer id;

    private String name;
}
