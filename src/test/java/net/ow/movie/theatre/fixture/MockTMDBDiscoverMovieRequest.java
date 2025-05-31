package net.ow.movie.theatre.fixture;

import java.time.Instant;
import net.ow.movie.tmdb.model.discover.TMDBDiscoverMovieRequest;

public class MockTMDBDiscoverMovieRequest {
    public static TMDBDiscoverMovieRequest mock(
            Integer primaryReleaseYear,
            Instant primaryReleaseDateLessThen,
            Integer page,
            String language) {
        TMDBDiscoverMovieRequest tmdbDiscoverMovieRequest = new TMDBDiscoverMovieRequest();

        tmdbDiscoverMovieRequest.setPrimaryReleaseYear(primaryReleaseYear);
        tmdbDiscoverMovieRequest.setPrimaryReleaseDateLessThen(primaryReleaseDateLessThen);
        tmdbDiscoverMovieRequest.setPage(page);
        tmdbDiscoverMovieRequest.setLanguage(language);

        return tmdbDiscoverMovieRequest;
    }
}
