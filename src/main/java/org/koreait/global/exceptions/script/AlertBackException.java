package org.koreait.global.exceptions.script;

import lombok.Getter;
import org.koreait.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

@Getter
public class AlertBackException extends CommonException {
    private final String target;

    public AlertBackException(String message, HttpStatus status, String target) {
        super(message, status);
        this.target = target;
    }

    public AlertBackException(String message, HttpStatus status) {
        this(message, status, "self");
    }

    public AlertBackException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}
