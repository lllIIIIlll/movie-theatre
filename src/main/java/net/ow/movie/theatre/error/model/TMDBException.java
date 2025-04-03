package net.ow.movie.theatre.error.model;

import net.ow.shared.errorutils.model.ErrorProperties;
import net.ow.shared.errorutils.model.ServiceError;
import org.springframework.http.HttpStatus;

public enum TMDBException implements ServiceError {
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");

    private ErrorProperties errorProperties;

    private static final String CODE_PREFIX = "TMDB";

    TMDBException(HttpStatus status, String code) {
        initialise(status, code);
    }

    @Override
    public HttpStatus getStatus() {
        return errorProperties.getStatus();
    }

    @Override
    public String getCode() {
        return errorProperties.getCode();
    }

    @Override
    public String getTitle() {
        return errorProperties.getTitle();
    }

    @Override
    public String getMessage() {
        return errorProperties.getMessage();
    }

    @Override
    public String getCodePrefix(HttpStatus status) {
        return status.value() + "-" + CODE_PREFIX + "-";
    }

    @Override
    public void setErrorProperties(ErrorProperties errorProperties) {
        this.errorProperties = errorProperties;
    }
}
