package com.lanxinbase.system.exception;

import com.lanxinbase.system.basic.ExceptionError;

/**
 * Created by alan.luo on 2017/9/18.
 */
public class IllegalValidateException extends ExceptionError {

    public IllegalValidateException(String error) {
        super(error);
    }

    public IllegalValidateException(Integer code) {
        super(code);
    }
}
