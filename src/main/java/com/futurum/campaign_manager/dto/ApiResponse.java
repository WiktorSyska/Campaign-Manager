package com.futurum.campaign_manager.dto;

/**
 * Generic API response wrapper for standardizing service responses.
 * <p>
 * This class provides a consistent structure for all API responses, containing:
 * <ul>
 *   <li>Success status flag indicating operation outcome</li>
 *   <li>Message providing additional context or error information</li>
 *   <li>Generic data payload containing the response content</li>
 * </ul>
 * </p>
 *
 * <p>
 * Includes convenience static factory methods for common response patterns:
 * <ul>
 *   <li>{@link #success(Object)} - Standard success response with data</li>
 *   <li>{@link #success(String, Object)} - Success response with custom message</li>
 *   <li>{@link #error(String)} - Standard error response</li>
 * </ul>
 * </p>
 *
 * <p>
 * Supports method chaining through the {@link #data(Object)} method for building
 * complex responses incrementally.
 * </p>
 *
 * @param <T> Type of the data payload contained in the response
 */
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message);
    }

    public ApiResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
