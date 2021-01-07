package com.bchevoor.incrementer.auth;

/**
 * An exception for when an operation cannot be completed because the user already exists.
 */
public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String s) {
        super(s);
    }

}
