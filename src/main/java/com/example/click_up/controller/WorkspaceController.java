package com.example.click_up.controller;

import com.example.click_up.entity.UserEntity;
import com.example.click_up.enums.Language;
import com.example.click_up.payload.*;
import com.example.click_up.security.CurrentUser;
import com.example.click_up.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;


@RestController
@RequestMapping("/api/workspace")
@Tag(name = "WorkspaceController", description = "This controller for adding Workspace")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }




    /**
     * This method is used for viewing all the workspace data if not founded
     * throw new WorkspaceNotFoundException
     *
     * @param language Language
     * @return List<ResponseWorkspaceList></>
     */
    @GetMapping("/get_all_workspace")
    @Operation(summary = "View_All_Workspace API", description = "This API for viewing all workspace")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Already Exist"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")}
    )
    public ResponseEntity<?> getAllWorkspace(
            @RequestHeader(name = "Accept-Language", defaultValue = "UZ") Language language) {
        List<ResponseWorkspaceList> list = workspaceService.getAllWorkspace(language);
        return ResponseEntity.ok(list);
    }

    /**
     * This method is used for viewing workspace data by id if not founded
     * throw  new WorkspaceNotFoundException
     *
     * @param id       Long
     * @param language Language
     * @return ResponseWorkspaceList
     */
    @GetMapping("/get_workspaceById/{id}")
    @Operation(summary = "View_All_Workspace API", description = "This API for viewing all workspace")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Already Exist"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")}
    )
    public ResponseEntity<?> getWorkspaceById(@PathVariable Long id,
                                              @RequestHeader(name = "Accept-Language", defaultValue = "UZ") Language language) {

        ResponseWorkspaceList workspace = workspaceService.getWorkspaceById(id, language);
        return ResponseEntity.ok(workspace);
    }




    /**
     * This method is used for adding workspace If the workspace name exist throw new ExistWorkspaceException
     *
     * @param workspaceDto WorkspaceDto
     * @param language     Language
     * @param currentUser  User
     * @return ResponseWorkspaceDto
     * @throws FileNotFoundException if file not found
     **/
    @PostMapping(value = "/add_workspace", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "Addworkspace API", description = "This API for adding new workspace")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Already Exist"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")}
    )
    public ResponseEntity<?> addWorkspace(@Valid @RequestBody WorkspaceDto workspaceDto, @CurrentUser UserEntity currentUser,
                                          @RequestHeader(name = "Accept-Language", defaultValue = "UZ") Language language) throws FileNotFoundException {
        ResponseWorkspaceDto addWorkspace = workspaceService.addWorkspace(workspaceDto, language, currentUser);
        return ResponseEntity.status(201).body(addWorkspace);
    }





    /**
     * This method is used for edting workspace data If not found the workspace data
     * throw new WorkspaceNotFoundException
     *
     * @param id           Long
     * @param workspaceDto WorkspaceDto
     * @param language     Language
     * @return ResponseWorkspaceDto
     */
    @PutMapping(value = "/edite_workspace/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "EditeWorkspace API", description = "This API for editing  workspace")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Already Exist"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")}
    )
    public ResponseEntity<?> editeWorkspace(@PathVariable Long id, @Valid @RequestBody WorkspaceDto workspaceDto,
                                            @RequestHeader(name = "Accept-Language", defaultValue = "UZ") Language language) {
        EditedResponseDto editeWorkspace = workspaceService.editeWorkspace(id, workspaceDto, language);
        return ResponseEntity.ok(editeWorkspace);
    }




    /**
     * This method is used for deleting Workspace data in db if not found Workspace data\
     * throw new WorkspaceNotFoundException
     *
     * @param id       Long
     * @param language Language
     * @return DeleteWorkspaceDto
     */
    @DeleteMapping(value = "/delete_workspace/{id}", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "DeleteWorkspace API", description = "This API for deleting  workspace")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Already Exist"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")}
    )
    public ResponseEntity<?> deleteWorkspace(@PathVariable Long id,
                                             @RequestHeader(name = "Accept-Language", defaultValue = "UZ") Language language) {
        DeleteWorkSpaceDto deleteWorkspace = workspaceService.deleteWorkspace(id, language);
        return ResponseEntity.ok(deleteWorkspace);
    }



}
