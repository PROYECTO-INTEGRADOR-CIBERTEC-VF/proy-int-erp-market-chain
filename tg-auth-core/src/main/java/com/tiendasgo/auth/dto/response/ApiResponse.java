package com.tiendasgo.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private String message;
    private T data;
    private String error;
    private String path;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(LocalDateTime.now(), message, data, null, null);
    }

    public static <T> ApiResponse<T> error(String error, String message, String path) {
        return new ApiResponse<>(LocalDateTime.now(), message, null, error, path);
    }
}

