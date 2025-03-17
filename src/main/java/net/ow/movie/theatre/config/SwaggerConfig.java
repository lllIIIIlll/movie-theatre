package net.ow.movie.theatre.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    @SuppressWarnings("all")
    private static String API_DOCUMENTATION_PATH = "api-doc/movie-theatre-service.yaml";

    @Bean
    public OpenAPI customOpenAPI() {
        ClassLoader classLoader = getClass().getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream(API_DOCUMENTATION_PATH);
        if (null == inputStream) {
            log.error(
                    "Failed to load OpenAPI definition from classpath - {}",
                    API_DOCUMENTATION_PATH);
            throw new RuntimeException(
                    "Failed to load OpenAPI definition from classpath - " + API_DOCUMENTATION_PATH);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String content = reader.lines().collect(Collectors.joining("\n"));

        SwaggerParseResult result = new OpenAPIV3Parser().readContents(content);
        if (result.getMessages().isEmpty() && result.getOpenAPI() != null) {
            return result.getOpenAPI();
        }

        log.error("Failed to parse OpenAPI definition with error - {}", result.getMessages());
        throw new RuntimeException("Failed to parse OpenAPI definition: " + result.getMessages());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api-docs/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/springdoc-openapi-ui/");
    }
}
