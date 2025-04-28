package com.sandipsky.inventory_system.util;
import com.sandipsky.inventory_system.dto.ApiResponse;

public class ResponseUtil {

    public static <T> ApiResponse<T> success(int data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        response.setErrorCode(0); // No error
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }

    public static <T> ApiResponse<T> error(String message, int errorCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setErrorCode(errorCode);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }
}
