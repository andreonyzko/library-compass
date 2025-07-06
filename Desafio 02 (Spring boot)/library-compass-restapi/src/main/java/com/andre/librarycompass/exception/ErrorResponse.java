package com.andre.librarycompass.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor

public class ErrorResponse {
    private LocalDateTime moment;
    private Integer status;
    private String error, message, path;
}
