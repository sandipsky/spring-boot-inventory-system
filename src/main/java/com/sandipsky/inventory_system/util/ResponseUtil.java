package com.sandipsky.inventory_system.util;

import java.util.Arrays;
import java.util.List;

import com.sandipsky.inventory_system.dto.ApiResponse;

public class ResponseUtil {

    public static <T> ApiResponse<T> success(int data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        response.setErrors(null);
        response.setErrorCode(0); // No error
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }

    public static <T> ApiResponse<T> error(List<String> errors, String message, int errorCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setErrors(errors);
        response.setErrorCode(errorCode);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }

    public static <T> ApiResponse<T> error(String error, String message, int errorCode) {
        return error(Arrays.asList(error), message, errorCode);
    }
}
