package com.example.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by dingue on 2018-03-04.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationFailException extends AuthenticationException {
    public AuthenticationFailException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthenticationFailException(String msg) {
        super(msg);
    }
}