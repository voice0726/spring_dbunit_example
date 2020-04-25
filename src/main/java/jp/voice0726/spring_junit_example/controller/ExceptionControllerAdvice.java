package jp.voice0726.spring_junit_example.controller;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by akinori on 2020/04/26
 *
 * @author akinori
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleException(NoHandlerFoundException e) {
        LOGGER.warn(e.getMessage());
        return "error/404";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleException(AccessDeniedException e) {
        LOGGER.warn(e.getMessage());
        return "error/403";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e, Model model) {
        e.printStackTrace();
        LOGGER.error(e.getMessage());
        model.addAttribute("error", e.getMessage());
        model.addAttribute("cause", ExceptionUtils.getCause(e));
        return "error/500";
    }
}
