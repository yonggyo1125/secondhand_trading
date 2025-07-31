package org.koreait.board.exceptions;

import org.koreait.global.exceptions.script.AlertBackException;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends AlertBackException {
    public CommentNotFoundException() {
        super("NotFound.comment", HttpStatus.NOT_FOUND);
    }
}
