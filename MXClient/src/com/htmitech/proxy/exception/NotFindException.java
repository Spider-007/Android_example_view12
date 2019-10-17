package com.htmitech.proxy.exception;

/**
 * Created by htrf-pc on 2016/11/28.
 */
public class NotFindException extends RuntimeException{

    private static final long serialVersionUID = 533854535537735837L;
    public NotFindException(String message) {
        super(message);
    }
}
