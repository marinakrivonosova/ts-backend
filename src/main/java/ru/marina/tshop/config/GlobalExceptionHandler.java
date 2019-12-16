package ru.marina.tshop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(final RuntimeException ex) {
        logger.error("Error occurred", ex);
    }

    @ExceptionHandler(Exception.class)
    public void handleException(final Exception ex) {
        logger.error("Error occurred", ex);
    }
}
