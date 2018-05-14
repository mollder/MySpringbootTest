package com.example.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by dingue on 2018-03-03.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request data is not right")
public class BadUserRequestException extends RuntimeException {
}