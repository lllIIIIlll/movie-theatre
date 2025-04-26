package net.ow.movie.theatre.dto.tv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.ow.movie.theatre.dto.person.CastDTO;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TVSeasonDTO extends BaseTVSeasonDTO {
    private List<TVBaseEpisode> episodes;

    private List<CastDTO> cast;
}
