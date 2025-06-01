package net.ow.movie.theatre.dto.language;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageDTO {
    private String iosCode;

    private String name;
}
