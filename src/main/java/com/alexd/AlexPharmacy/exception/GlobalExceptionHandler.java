package com.alexd.AlexPharmacy.exception;

import com.alexd.AlexPharmacy.exception.basket.BasketNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handler for mismatch of argument types.
     *
     * @param ex      - caught MethodArgumentTypeMismatchException
     * @param request - the current request
     */
    @ExceptionHandler({BasketNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(final @NotNull Exception ex, final WebRequest request) {
        List<String> errors;

        if (ex instanceof BasketNotFoundException) {
            errors = Collections.singletonList("Не знайдено кошик за номером №" + ex.getLocalizedMessage());
        } else {
            errors = Collections.singletonList("Помилки додаються");
        }

        var apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * Handler for binding exceptions and/or argument not valid exceptions.
     */
    @Override
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(
            @NotNull final MethodArgumentNotValidException ex,
            @NotNull final HttpHeaders headers,
            @NotNull final HttpStatus status,
            @NotNull final WebRequest request) {
        var errors = new ArrayList<String>();

        errors.addAll(ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList()));

        errors.addAll(ex.getBindingResult().getGlobalErrors()
                .stream()
                .map(error -> error.getObjectName() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList()));

        var apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    /**
     * Handler for bean validation errors.
     *
     * @param ex      - caught ConstraintViolationException
     * @param request - the current request
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            final @NotNull ConstraintViolationException ex, final WebRequest request) {
        var errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getRootBeanClass().getName() + " "
                        + violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toCollection(ArrayList::new));

        var apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * Handler for mismatch of argument types.
     *
     * @param ex      - caught MethodArgumentTypeMismatchException
     * @param request - the current request
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            final @NotNull MethodArgumentTypeMismatchException ex, final WebRequest request) {
        var error = ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();

        var apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * Handler NoHandlerFoundException.
     */
    @Override
    protected @NotNull ResponseEntity<Object> handleNoHandlerFoundException(
            @NotNull final NoHandlerFoundException ex, @NotNull final HttpHeaders headers,
            @NotNull final HttpStatus status, @NotNull final WebRequest request) {
        var error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        var apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * Handler HttpRequestMethodNotSupportedException –
     * which occurs when you send a requested with an unsupported HTTP method.
     */
    @Override
    protected @NotNull ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            @NotNull final HttpRequestMethodNotSupportedException ex, @NotNull final HttpHeaders headers,
            @NotNull final HttpStatus status, @NotNull final WebRequest request) {
        var builder = new StringBuilder();
        builder.append(ex.getMethod()).append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t).append(" "));

        var apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * Handler HttpMediaTypeNotSupportedException –
     * which occurs when the client send a request with unsupported media type.
     */
    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            @NotNull final HttpMediaTypeNotSupportedException ex, @NotNull final HttpHeaders headers,
            @NotNull final HttpStatus status, @NotNull final WebRequest request) {
        var builder = new StringBuilder();
        builder.append(ex.getContentType()).append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        var apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(),
                builder.substring(0, builder.length() - 2));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * Default handler for catch-all type of logic.
     *
     * @param ex      - caught MethodArgumentTypeMismatchException
     * @param request - the current request
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(@NotNull final Exception ex, final WebRequest request) {
        var apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
