package net.ow.movie.theatre.error.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import java.nio.ByteBuffer;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ow.movie.theatre.error.model.TMDBException;
import net.ow.movie.tmdb.model.error.TMDBError;
import net.ow.shared.errorutils.model.APIException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TMDBExceptionHandler {
    private static final String NO_RESPONSE_EXCEPTION_MESSAGE = "No response from TMDB";

    private static final String UNKNOWN_EXCEPTION_MESSAGE = "Unknown error from TMDB";

    private final ObjectMapper objectMapper;

    @Around("execution(* net.ow.movie.tmdb.feign.TMDBFeignClient.*(..))")
    public Object handleTMDBException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (FeignException e) {
            int status = e.status();
            String errorMessage = extractErrorMessage(e);

            if (HttpStatus.UNAUTHORIZED.value() == status) {
                log.error("Unauthorized request to TMDB - {}", errorMessage);
                throw new APIException(TMDBException.UNAUTHORIZED);
            }

            if (HttpStatus.NOT_FOUND.value() == status) {
                log.error("Resource not found in TMDB - {}", errorMessage);
                throw new APIException(TMDBException.RESOURCE_NOT_FOUND);
            }

            if (HttpStatus.SERVICE_UNAVAILABLE.value() == status) {
                log.error("TMDB service unavailable.");
                throw new APIException(TMDBException.SERVICE_UNAVAILABLE);
            }

            log.error(e.getMessage());
            log.error("Failed to execute TMDB request - {}", errorMessage);
            throw new APIException(TMDBException.INTERNAL_SERVER_ERROR, errorMessage);
        }
    }

    private String extractErrorMessage(FeignException exception) {
        Optional<ByteBuffer> responseBody = exception.responseBody();

        if (responseBody.isEmpty()) {
            return NO_RESPONSE_EXCEPTION_MESSAGE;
        }

        String errorStr = new String(responseBody.get().array());

        TMDBError error;
        try {
            error = objectMapper.readValue(errorStr, TMDBError.class);
        } catch (Exception ignore) {
            return errorStr;
        }

        String errorMessage = error.getStatusMessage();
        if (null == errorMessage || errorMessage.isBlank()) {
            return UNKNOWN_EXCEPTION_MESSAGE;
        }

        return errorMessage;
    }
}
