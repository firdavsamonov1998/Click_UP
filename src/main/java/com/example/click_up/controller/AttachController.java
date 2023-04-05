package com.example.click_up.controller;

import com.example.click_up.entity.Attachment;
import com.example.click_up.service.AttachService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "AttachController", description = "This controller for File System")
@RestController
@RequestMapping("/api/attach")
public class AttachController {

    private final AttachService attachService;

    @Autowired
    public AttachController(AttachService attachService) {
        this.attachService = attachService;
    }




    @PostMapping(value = "/upload", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "Upload File API", description = "This API for uploading file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Already Exist"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    public ResponseEntity<?> uploadFile(@ModelAttribute("file") MultipartFile multipartFile) {
        Attachment attachment = attachService.uploadFile(multipartFile);
        return ResponseEntity.ok(attachment);
    }

    @PostMapping(value = "/download", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "Download File API", description = "This API for downloading file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Already Exist"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    public ResponseEntity<?> downloadFile() {
        Attachment attachment = attachService.downloadFile();
        return ResponseEntity.ok(attachment);
    }
}
