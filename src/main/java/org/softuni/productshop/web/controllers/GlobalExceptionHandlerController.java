package org.softuni.productshop.web.controllers;

import org.softuni.productshop.web.exception.ProductNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandlerController extends BaseController {

    @ExceptionHandler({ProductNotFoundException.class, SQLException.class})
    public ModelAndView handleProductNotFoundException(RuntimeException e){
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());

        return modelAndView;
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
public ModelAndView handleDBException(SQLException e){
        
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getSQLState());
        return modelAndView;

}


}
