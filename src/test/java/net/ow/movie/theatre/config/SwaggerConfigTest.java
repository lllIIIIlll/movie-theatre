package net.ow.movie.theatre.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.swagger.v3.oas.models.OpenAPI;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@ExtendWith(MockitoExtension.class)
class SwaggerConfigTest {
    @InjectMocks private SwaggerConfig swaggerConfig;

    @Mock private ResourceHandlerRegistry registry;

    @Mock private ResourceHandlerRegistration registration;

    @Test
    @SneakyThrows
    void customOpenAPITest_OK() {
        OpenAPI result = swaggerConfig.customOpenAPI();

        assertNotNull(result);
        assertEquals("Movie Theatre Service", result.getInfo().getTitle());
    }

    @Test
    void customOpenAPITest_whenResourceNotFound_thenThrowsException() {
        ReflectionTestUtils.setField(swaggerConfig, "API_DOCUMENTATION_PATH", "invalid-path");

        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> swaggerConfig.customOpenAPI());

        assertNotNull(exception.getMessage());
        assertTrue(
                exception
                        .getMessage()
                        .contains("Failed to load OpenAPI definition from classpath"));
    }

    @Test
    void customOpenAPITest_whenParsingFails_thenThrowsException() {
        ReflectionTestUtils.setField(
                swaggerConfig,
                "API_DOCUMENTATION_PATH",
                "api-doc/invalid-movie-theatre-service.yaml");

        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> swaggerConfig.customOpenAPI());

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Failed to parse OpenAPI definition"));
    }

    @Test
    void addResourceHandlersTest_OK() {
        when(registry.addResourceHandler("/api-docs/**")).thenReturn(registration);
        when(registry.addResourceHandler("/swagger-ui/**")).thenReturn(registration);

        swaggerConfig.addResourceHandlers(registry);

        verify(registration).addResourceLocations("classpath:/META-INF/resources/");
        verify(registration)
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/springdoc-openapi-ui/");
    }
}
