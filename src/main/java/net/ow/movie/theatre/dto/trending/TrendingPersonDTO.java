package net.ow.movie.theatre.dto.trending;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrendingPersonDTO extends TrendingDTO {
    private Integer id;

    private String mediaType;
}
