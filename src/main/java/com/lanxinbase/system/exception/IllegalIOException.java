package com.lanxinbase.system.exception;

import com.lanxinbase.system.basic.ExceptionError;

/**
 * Created by alan.luo on 2017/9/18.
 */
public class IllegalIOException extends ExceptionError {

    public IllegalIOException(String error) {
        super(error);
    }

    public IllegalIOException(Integer code) {
        super(code);
    }
}
