package com.example.click_up.controller;

import com.example.click_up.enums.Language;
import com.example.click_up.payload.*;
import com.example.click_up.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Already Exist"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto
            , @RequestHeader(name = "Accept-Language", defaultValue = "UZ") Language language) {
        ResponseRegisterDto responseRegisterDto = authService.registerUser(registerUserDto, language);
        return ResponseEntity.status(201).body(responseRegisterDto);
    }


    /**
     * This method for Login To System
     * If the password or login is wrong is  throw UserNotFoundException and status 404
     *
     * @param loginDto LoginDto
     * @return ResponseLoginDto
     */
    @PostMapping("/login")
    @Operation(summary = "Login To System  API", description = "This API for Login To System" +
            "\nIf the username or password is Wrong  throw UserNoFoundException")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Already Exist"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    public ResponseEntity<?> loginToSystem(@Valid @RequestBody LoginDto loginDto,
                                           @RequestHeader(name = "Accept-Language", defaultValue = "UZ") Language language) {
        ResponseLoginDto responseLoginDto = authService.loginToSystem(loginDto, language);
        return ResponseEntity.ok(responseLoginDto);
    }


    /**
     * This method is used for verifying email If the username is not exist
     * throw UserNotFoundException
     *
     * @param email     String
     * @param emailCode String
     * @param language  Language
     * @return EmailResponse
     */

    @PostMapping("/email")
    @Operation(summary = "Verifying Email API", description = "This API is used for verifying email Code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Already Exist"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    public ResponseEntity<?> verifyEmail(@RequestParam String email, @RequestParam String emailCode,
                                         @RequestHeader(name = "Accept-Language", defaultValue = "UZ") Language language) {
        EmailResponse emailResponse = authService.verifyEmail(email, emailCode, language);
        if (emailResponse.isSuccess()) {
            return ResponseEntity.ok(emailResponse);
        }
        return ResponseEntity.status(404).body(emailResponse);
    }

}
