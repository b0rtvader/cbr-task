package ru.bortnik.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SubscriberException extends RuntimeException {

    public SubscriberException() {
    }

    public SubscriberException(String message) {
        super(message);
    }

    public SubscriberException(Throwable cause) {
        super(cause);
    }
}
