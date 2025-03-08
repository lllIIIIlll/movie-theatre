package net.ow.movie.theatre.mapper;

import java.util.List;

public interface SingleDirectionalMapper<Source, Target> {
    Target map(Source source);

    List<Target> map(List<Source> sources);
}
