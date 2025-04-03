package net.ow.movie.theatre.error.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.nimbusds.jose.shaded.gson.Gson;
import feign.FeignException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import lombok.SneakyThrows;
import net.ow.movie.theatre.error.model.TMDBException;
import net.ow.shared.errorutils.model.APIException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class TMDBExceptionHandlerTest {
    @InjectMocks private TMDBExceptionHandler tmdbExceptionHandler;

    @Mock private ProceedingJoinPoint joinPoint;

    @Mock private FeignException feignException;

    @Spy private Gson gson;

    @Test
    void handleTMDBExceptionTest_whenNormalExecution_thenReturnsResult() throws Throwable {
        Object expectedResult = new Object();
        when(joinPoint.proceed()).thenReturn(expectedResult);

        Object result = tmdbExceptionHandler.handleTMDBException(joinPoint);

        assertEquals(expectedResult, result);
        verify(joinPoint, times(1)).proceed();
    }

    @Test
    @SneakyThrows
    void handleTMDBExceptionTest_whenFeignExceptionUnauthorized_thenThrowsAPIException() {
        when(joinPoint.proceed()).thenThrow(feignException);
        when(feignException.status()).thenReturn(HttpStatus.UNAUTHORIZED.value());

        APIException exception =
                assertThrows(
                        APIException.class,
                        () -> tmdbExceptionHandler.handleTMDBException(joinPoint));

        assertEquals(TMDBException.UNAUTHORIZED, exception.getError());
        verify(joinPoint, times(1)).proceed();
    }

    @Test
    @SneakyThrows
    void handleTMDBExceptionTest_whenFeignExceptionNotFound_thenThrowsAPIException() {
        when(joinPoint.proceed()).thenThrow(feignException);
        when(feignException.status()).thenReturn(HttpStatus.NOT_FOUND.value());

        APIException exception =
                assertThrows(
                        APIException.class,
                        () -> tmdbExceptionHandler.handleTMDBException(joinPoint));

        assertEquals(TMDBException.RESOURCE_NOT_FOUND, exception.getError());
        verify(joinPoint, times(1)).proceed();
    }

    @Test
    @SneakyThrows
    void handleTMDBExceptionTest_whenFeignExceptionServiceUnavailable_thenThrowsAPIException() {
        when(joinPoint.proceed()).thenThrow(feignException);
        when(feignException.status()).thenReturn(HttpStatus.SERVICE_UNAVAILABLE.value());

        APIException exception =
                assertThrows(
                        APIException.class,
                        () -> tmdbExceptionHandler.handleTMDBException(joinPoint));

        assertEquals(TMDBException.SERVICE_UNAVAILABLE, exception.getError());
        verify(joinPoint, times(1)).proceed();
    }

    @Test
    @SneakyThrows
    void handleTMDBExceptionTest_whenFeignExceptionOther_thenThrowsFeignException() {
        when(joinPoint.proceed()).thenThrow(feignException);
        when(feignException.status()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());

        APIException exception =
                assertThrows(
                        APIException.class,
                        () -> tmdbExceptionHandler.handleTMDBException(joinPoint));

        assertEquals(TMDBException.INTERNAL_SERVER_ERROR, exception.getError());
        verify(joinPoint, times(1)).proceed();
    }

    @Test
    @SneakyThrows
    void handleTMDBExceptionTest_whenFeignExceptionInternalServerError_thenThrowsAPIException() {
        String errorMessage = "{\"statusMessage\":\"Internal Server Error\",\"statusCode\":500}";

        when(joinPoint.proceed()).thenThrow(feignException);
        when(feignException.status()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());
        when(feignException.responseBody())
                .thenReturn(
                        Optional.of(
                                ByteBuffer.wrap(errorMessage.getBytes(StandardCharsets.UTF_8))));

        APIException exception =
                assertThrows(
                        APIException.class,
                        () -> tmdbExceptionHandler.handleTMDBException(joinPoint));

        assertEquals(TMDBException.INTERNAL_SERVER_ERROR, exception.getError());
        assertEquals("Internal Server Error", exception.getMessageParams()[0]);
    }

    @Test
    void
            handleTMDBExceptionTest_whenFeignExceptionEmptyResponseBody_thenThrowsAPIExceptionWithUseDefaultMessage()
                    throws Throwable {
        when(joinPoint.proceed()).thenThrow(feignException);
        when(feignException.status()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());
        when(feignException.responseBody()).thenReturn(Optional.empty());

        // When & Then
        APIException exception =
                assertThrows(
                        APIException.class,
                        () -> tmdbExceptionHandler.handleTMDBException(joinPoint));
        assertEquals(TMDBException.INTERNAL_SERVER_ERROR, exception.getError());
        assertEquals("No response from TMDB.", exception.getMessageParams()[0]);
    }

    @Test
    void handleTMDBExceptionTest_whenInvalidJson_thenUseRawResponse() throws Throwable {
        String errorMessage = "This is not a valid JSON";

        when(joinPoint.proceed()).thenThrow(feignException);
        when(feignException.status()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());
        when(feignException.responseBody())
                .thenReturn(
                        Optional.of(
                                ByteBuffer.wrap(errorMessage.getBytes(StandardCharsets.UTF_8))));

        APIException exception =
                assertThrows(
                        APIException.class,
                        () -> tmdbExceptionHandler.handleTMDBException(joinPoint));
        assertEquals(TMDBException.INTERNAL_SERVER_ERROR, exception.getError());
        assertEquals(errorMessage, exception.getMessageParams()[0]);
    }
}
