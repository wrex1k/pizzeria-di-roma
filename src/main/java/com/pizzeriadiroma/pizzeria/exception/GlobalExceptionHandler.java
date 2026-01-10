package com.pizzeriadiroma.pizzeria.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404 – Resource not found (entity does not exist)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, "Not Found", exception.getMessage(), request);
    }

    // 404 – Page not found (no controller mapping)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNoHandler(NoHandlerFoundException exception, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, "Page Not Found",
                "The requested page does not exist.", request);
    }

    // 403 – Access denied (no permission)
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDenied(AccessDeniedException exception, HttpServletRequest request) {
        return build(HttpStatus.FORBIDDEN, "Access Denied",
                "You do not have permission to access this page.", request);
    }

    // 500 – Internal server error (unexpected error)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleServerError(Exception exception, HttpServletRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                "An unexpected error occurred. Please try again later.", request);
    }

    // 400 – Bad request (validation error)
    @ExceptionHandler(ValidationException.class)
    public ModelAndView handleValidation(ValidationException exception, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "Bad Request", exception.getMessage(), request);
    }

    private ModelAndView build(HttpStatus status, String title, String message, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error/error");
        mav.setStatus(status);
        mav.addObject("statusCode", status.value());
        mav.addObject("errorTitle", title);
        mav.addObject("errorMessage", message);
        mav.addObject("path", request.getRequestURI());
        return mav;
    }
}
