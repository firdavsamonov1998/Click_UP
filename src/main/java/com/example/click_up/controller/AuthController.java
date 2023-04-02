package com.example.click_up.controller;

import com.example.click_up.enums.Language;
import com.example.click_up.payload.RegisterUserDto;
import com.example.click_up.payload.ResponseRegisterDto;
import com.example.click_up.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "AuthController", description = "This controller for Authentication")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * This method for registration user
     * If the phone is already exist throw PhoneExistException and status 409
     * If the username is already exist throw UsernameExistException and status 409
     *
     * @param registerUserDto RegisterUserDto
     * @return ResponseRegisterDto
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register User API", description = "This API for Registration" +
            "\nIf the username is already exist throw UsernameExistException and status 409")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto
            , @RequestHeader(name = "Accept-Language") Language language) {
        ResponseRegisterDto responseRegisterDto = authService.registerUser(registerUserDto,language);
        return ResponseEntity.status(201).body(responseRegisterDto);
    }
}
