package net.ow.movie.theatre.mapper;

import net.ow.movie.theatre.dto.common.PagedResponse;
import org.mapstruct.*;

public interface PagedResponseMapper<Source, Target>
        extends BidirectionalMapper<
                net.ow.movie.tmdb.model.common.PagedResponse<Source>, PagedResponse<Target>> {
    @Override
    @Mapping(target = "data", source = "results")
    @Mapping(target = "total", source = "totalResults")
    PagedResponse<Target> mapTo(
            net.ow.movie.tmdb.model.common.PagedResponse<Source> tPagedResponse);

    @Override
    @Mapping(target = "results", source = "data")
    @Mapping(target = "totalResults", source = "total")
    net.ow.movie.tmdb.model.common.PagedResponse<Source> mapFrom(
            PagedResponse<Target> pagedResponse);
}
