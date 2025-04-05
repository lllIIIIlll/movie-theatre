package net.ow.movie.theatre.dto.trending;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TrendingPersonDTO extends TrendingDTO {
    private Integer id;

    private String mediaType;
}
