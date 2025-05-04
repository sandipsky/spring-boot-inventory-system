package com.sandipsky.inventory_system.exception;

import com.sandipsky.inventory_system.dto.ApiResponse;
import com.sandipsky.inventory_system.util.ResponseUtil;

import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
        ApiResponse<Object> response = ResponseUtil.error(ex.getMessage(), 500);
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicate(DuplicateResourceException ex) {
        ApiResponse<Object> response = ResponseUtil.error(ex.getMessage(), 409);
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiResponse<Object> response = ResponseUtil.error(ex.getMessage(), 404);
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(ResponseNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResponseNotFound(ResponseNotFoundException ex) {
        ApiResponse<Object> response = ResponseUtil.error(ex.getMessage(), 204);
        return ResponseEntity.status(204).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentials(BadCredentialsException ex) {
        ApiResponse<Object> response = ResponseUtil.error("Username or password incorrect", 401);
        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccountStatus(AccountStatusException ex) {
        ApiResponse<Object> response = ResponseUtil.error("The account is locked", 403);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        ApiResponse<Object> response = ResponseUtil.error("You are not authorized to access this resource", 403);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<Object>> handleExpiredJwt(ExpiredJwtException ex) {
        ApiResponse<Object> response = ResponseUtil.error("The JWT token has expired", 403);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
