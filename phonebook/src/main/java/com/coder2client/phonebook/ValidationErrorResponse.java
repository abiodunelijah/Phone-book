package com.coder2client.phonebook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {
    
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String statusCode;
    private Map<String, String> fieldErrors;
}
