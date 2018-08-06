package com.lanxinbase.system.exception;

import com.lanxinbase.system.basic.ExceptionError;

/**
 * Created by alan.luo on 2017/9/18.
 */
public class IllegalServiceException extends ExceptionError {

    public IllegalServiceException(String error) {
        super(error);
    }

    public IllegalServiceException(Integer code) {
        super(code);
    }
}
