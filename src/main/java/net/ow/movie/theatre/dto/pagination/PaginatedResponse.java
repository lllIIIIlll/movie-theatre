package net.ow.movie.theatre.dto.pagination;

import java.util.List;
import lombok.Data;

@Data
public class PaginatedResponse<T> {
    private List<T> data;

    private Integer page;

    private Integer totalPages;

    private Integer total;
}
