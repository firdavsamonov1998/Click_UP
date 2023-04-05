package com.example.click_up.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class WorkspaceDto {
    @NotBlank
    @NotNull
    private String name;
    @NotNull
    @NotBlank
    private String color;

    private MultipartFile avatar;
}
