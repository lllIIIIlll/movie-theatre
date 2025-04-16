package net.ow.movie.theatre.dto.genre;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreDTO {
    private Integer id;

    private String name;

    public GenreDTO(Integer id) {
        this.id = id;
    }
}
