package com.cr.usuario.config;

import brave.Tracer;
import com.cr.usuario.config.exception.BadRequestException;
import com.cr.usuario.config.exception.NotFoundException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    private static final String X_B3_TRACE_ID = "X-B3-TraceId";
    private static final String X_B3_SPAN_ID = "X-B3-SpanId";
    private static final String DEVELOPMENT_PROFILE = "desa";
    private static final String LOCAL_PROFILE = "local";
    private final HttpServletRequest httpServletRequest;
    private final Tracer tracer;

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    public ErrorHandler(final HttpServletRequest httpServletRequest, final Tracer tracer) {
        this.httpServletRequest = httpServletRequest;
        this.tracer = tracer;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorResponse> handle(Throwable ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handle(BadRequestException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handle(MethodArgumentNotValidException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorResponse> handle(ValidationException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handle(NotFoundException ex) {
        log.error(HttpStatus.NOT_FOUND.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.NOT_FOUND, ex);
    }

    private ResponseEntity<ApiErrorResponse> buildResponseError(HttpStatus httpStatus, Throwable ex) {

        final var traceId = Optional.ofNullable(this.tracer.currentSpan())
            .map(span -> span.context().traceIdString())
            .orElse(TraceSleuthInterceptor.TRACE_ID_NOT_EXISTS);

        final var spandId = Optional.ofNullable(this.tracer.currentSpan())
            .map(span -> span.context().spanIdString())
            .orElse(TraceSleuthInterceptor.SPAND_ID_NOT_EXISTS);

        final var metaData = Map.of(X_B3_TRACE_ID, traceId,
            X_B3_SPAN_ID, spandId);

        final var apiErrorResponse = ApiErrorResponse
            .builder()
            .message(ex.getMessage())
            .status(httpStatus.value())
            .metadata(metaData)
            .build();

        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    @Builder
    @NonNull
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ApiErrorResponse {

        private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSSSSS]['Z']";

        @JsonProperty
        private Integer status;
        @JsonProperty
        private String message;
        @JsonProperty
        private Map<String, String> metadata;
    }
}

