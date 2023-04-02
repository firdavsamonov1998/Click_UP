package com.example.click_up.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RegisterUserDto {
    @NotBlank(message = "fullName cannot be null or empty")
    @NotNull(message = "fullName cannot be null or empty")
    private String fullName;

    @NotBlank(message = "username cannot be null or empty")
    @NotNull(message = "username cannot be null or empty")
    private String username;

    @NotBlank(message = "username cannot be null or empty")
    @NotNull(message = "username cannot be null or empty")
    private String password;
}
