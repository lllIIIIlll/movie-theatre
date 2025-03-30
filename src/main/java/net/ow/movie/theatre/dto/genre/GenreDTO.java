package net.ow.movie.theatre.dto.genre;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenreDTO {
    private Integer id;

    private String name;

    public GenreDTO(Integer id) {
        this.id = id;
    }
}
