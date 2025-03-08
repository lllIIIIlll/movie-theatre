package net.ow.movie.theatre.dto.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonSearchResultDTO extends SearchResultDTO {
    protected Integer id;

    protected String mediaType;

    private String name;

    private String overview;

    private String posterPath;
}
