package com.Nikola.ECommerce.Exceptions;

import org.springframework.web.bind.annotation.RestControllerAdvice;


public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message)
    {
        super(message);
    }


}
