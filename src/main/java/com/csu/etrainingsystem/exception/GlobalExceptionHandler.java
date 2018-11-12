package com.csu.etrainingsystem.exception;

import com.csu.etrainingsystem.form.CommonResponseForm;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public CommonResponseForm defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        return CommonResponseForm.of400(e.getMessage());

    }

}
