package com.htmitech.proxy.exception;

/**
 * 找不到对应appID异常
 */
public class NotApplicationException extends RuntimeException{
    private static final long serialVersionUID = 533854535537735838L;
    public NotApplicationException(String message) {
        super(message);
    }
}
