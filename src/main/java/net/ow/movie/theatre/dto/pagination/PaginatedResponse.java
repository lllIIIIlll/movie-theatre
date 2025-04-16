package net.ow.movie.theatre.dto.pagination;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collections;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginatedResponse<T> {
    private List<T> data;

    private Integer page;

    private Integer totalPages;

    private Integer total;

    public PaginatedResponse() {
        this.data = Collections.emptyList();
        this.page = 1;
        this.totalPages = 1;
        this.total = 0;
    }
}
