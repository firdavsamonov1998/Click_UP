package com.example.click_up.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResponseRegisterDto {
    private String fullName;
    private boolean success;
    private Integer statusCode;
    private String message;
}
