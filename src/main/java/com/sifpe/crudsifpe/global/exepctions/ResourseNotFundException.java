package com.sifpe.crudsifpe.global.exepctions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourseNotFundException extends Exception{

    public ResourseNotFundException(String message){
        super(message);
    }
}
