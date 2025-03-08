package net.ow.movie.theatre.mapper;

import java.util.List;

public interface BidirectionalMapper<Source, Target> {
    Target mapTo(Source source);

    List<Target> mapTo(List<Source> sources);

    Source mapFrom(Target target);

    List<Source> mapFrom(List<Target> targets);
}
