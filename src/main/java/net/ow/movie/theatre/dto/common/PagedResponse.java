package net.ow.movie.theatre.dto.common;

import java.util.List;
import lombok.Data;

@Data
public class PagedResponse<T> {
    private List<T> data;

    private Integer total;

    private Integer page;

    private Integer totalPages;
}
