package in.gov.abdm.hip.controller;

import in.gov.abdm.controller.ABDMControllerAdvise;
import in.gov.abdm.error.ABDMError;
import in.gov.abdm.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Slf4j
public class HIPControllerAdvice {
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<List<ErrorResponse>> handleWebExchangeBindException(WebExchangeBindException exception) {
        return Mono.just(exception.getBindingResult().getAllErrors())
                .flatMapIterable(errors -> errors)
                .map(objectError -> new ErrorResponse(ABDMError.UNKNOWN_EXCEPTION.getCode(),
                        objectError.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<List<ErrorResponse>> handleGlobalValidationException(ConstraintViolationException exception) {
        List<ErrorResponse> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            errors.add(new ErrorResponse(ABDMError.CONSTRAINT_VIOLATION.getCode(), violation.getMessage()));
        }
        return Mono.just(errors);
    }

    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> exception(Exception exception) {
        return ABDMControllerAdvise.handleException(exception);

    }
}