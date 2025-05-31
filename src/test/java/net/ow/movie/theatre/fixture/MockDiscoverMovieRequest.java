package net.ow.movie.theatre.fixture;

import java.time.Instant;
import net.ow.movie.theatre.dto.movie.DiscoverMovieRequest;

public class MockDiscoverMovieRequest {
    public static DiscoverMovieRequest mock(
            Integer primaryReleaseYear, Instant primaryReleaseDateLessThen) {
        DiscoverMovieRequest discoverMovieRequest = new DiscoverMovieRequest();

        discoverMovieRequest.setPrimaryReleaseYear(primaryReleaseYear);
        discoverMovieRequest.setPrimaryReleaseDateLessThen(primaryReleaseDateLessThen);

        return discoverMovieRequest;
    }
}
