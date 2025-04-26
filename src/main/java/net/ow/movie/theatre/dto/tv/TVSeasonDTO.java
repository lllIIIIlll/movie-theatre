package net.ow.movie.theatre.dto.tv;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.ow.movie.theatre.dto.person.CastDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class TVSeasonDTO extends BaseTVSeasonDTO {
    private List<TVBaseEpisode> episodes;

    private List<CastDTO> cast;
}
