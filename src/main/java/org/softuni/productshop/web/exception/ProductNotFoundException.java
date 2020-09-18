package org.softuni.productshop.web.exception;

import org.softuni.productshop.domain.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product not found")
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(){
    }
    public ProductNotFoundException(String message){
        super(message);

    }
}
