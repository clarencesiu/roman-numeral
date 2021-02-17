package com.adobe.aem.exception;

/**
 * This is the exception thrown when an invalid request comes in.
 *
 * @author  Clarence Siu
 * @version 1.0
 * @since   2021-02-15
 */
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }

}
