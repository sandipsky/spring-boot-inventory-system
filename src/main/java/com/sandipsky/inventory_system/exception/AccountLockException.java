package com.sandipsky.inventory_system.exception;

import org.springframework.security.authentication.AccountStatusException;

public class AccountLockException extends AccountStatusException {

  public AccountLockException(String message) {
    super(message);
  }
}