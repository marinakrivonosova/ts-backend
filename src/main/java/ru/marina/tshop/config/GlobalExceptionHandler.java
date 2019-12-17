package ru.marina.tshop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public HttpServletResponse handleRuntimeException(final RuntimeException ex, final HttpServletResponse response) {
        logger.error("Error occurred", ex);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return response;
    }

    @ExceptionHandler(Exception.class)
    public HttpServletResponse handleException(final Exception ex, final HttpServletResponse response) {
        logger.error("Error occurred", ex);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return response;
    }
}
