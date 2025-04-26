package net.ow.movie.theatre.dto.tv;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.ow.movie.theatre.dto.person.CastDTO;
import net.ow.movie.theatre.dto.person.CreatorDTO;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TVShowDTO extends BaseTVShowDTO {
    private Integer numberOfEpisodes;

    private Integer numberOfSeasons;

    private String status;

    private String tagline;

    private List<BaseTVSeasonDTO> seasons;

    private List<CastDTO> cast;

    private List<CreatorDTO> createdBy;

    private List<BaseTVShowDTO> recommendations;
}
