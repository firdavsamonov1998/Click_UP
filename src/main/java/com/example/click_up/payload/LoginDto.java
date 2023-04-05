package com.example.click_up.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class LoginDto {

    @NotBlank
    @NotNull
    @Email
    private String username;

    @NotNull
    @NotBlank
    private String password;
}
