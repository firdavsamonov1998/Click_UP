package com.example.click_up.payload;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ResponseWorkspaceList {
    private Long id;
    private String name;
    private String color;
    private String initialLetter;
}
