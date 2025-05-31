package net.ow.movie.theatre.dto.movie;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscoverMovieRequest {
    private Integer primaryReleaseYear;

    private Instant primaryReleaseDateLessThen;

    private String region;

    private String withOriginalLanguage;

    private String withGenres;

    private String withoutGenres;

    private String sortBy;
}
