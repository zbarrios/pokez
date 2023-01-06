package com.pokez.exceptions;

public class AccAlreadyExistsException extends Exception {

  public AccAlreadyExistsException(String email) {
    super("The email: " + email + " is already registered, please use another email.");
  }

}
