package com.example.click_up.service;

import com.example.click_up.entity.Attachment;
import com.example.click_up.entity.UserEntity;
import com.example.click_up.entity.WorkSpace;
import com.example.click_up.enums.Language;
import com.example.click_up.exception.ExistWorkspaceException;
import com.example.click_up.exception.WorkspaceNotFoundException;
import com.example.click_up.payload.*;
import com.example.click_up.repository.WorkspaceRepository;
import com.example.click_up.util.ConvertToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceService {


    private final WorkspaceRepository workspaceRepository;
    private final ResourceBundleService resourceBundleService;
    private final AttachService attachService;
    private final ConvertToDto convertToDto;

    @Autowired
    public WorkspaceService(WorkspaceRepository workspaceRepository, ResourceBundleService resourceBundleService, AttachService attachService, ConvertToDto convertToDto) {
        this.workspaceRepository = workspaceRepository;
        this.resourceBundleService = resourceBundleService;
        this.attachService = attachService;
        this.convertToDto = convertToDto;
    }

    /**
     * This method is used for adding workspace If the workspace name exist throw new ExistWorkspaceException
     *
     * @param workspaceDto WorkspaceDto
     * @param language     Language
     * @param currentUser  User
     * @return ResponseWorkspaceDto
     * @throws FileNotFoundException if file not found
     */
    public ResponseWorkspaceDto addWorkspace(WorkspaceDto workspaceDto, Language language, UserEntity currentUser) throws FileNotFoundException {
        boolean exists = workspaceRepository.existsByOwnerIdAndName(currentUser.getId(), workspaceDto.getName());
        if (exists) {
            throw new ExistWorkspaceException(resourceBundleService.getMessage("workspace.exist", language));
        }
        Attachment attachment = attachService.uploadFile(workspaceDto.getAvatar());
        WorkSpace workSpace = new WorkSpace();
        workSpace.setName(workspaceDto.getName());
        workSpace.setOwner(currentUser);
        workSpace.setAvatar(attachment);
        workSpace.setColor(workspaceDto.getColor());
        WorkSpace savedWorkspace = workspaceRepository.save(workSpace);
        return convertToDto.responseWorkspaceDto(savedWorkspace);
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

    public EditedResponseDto editeWorkspace(Long id, WorkspaceDto workspaceDto, Language language) {

        Optional<WorkSpace> optional = workspaceRepository.findById(id);
        if (optional.isEmpty()) {
            throw new WorkspaceNotFoundException(resourceBundleService.getMessage("workspace.not.found", language));
        }
        Attachment attachment = attachService.uploadFile(workspaceDto.getAvatar());
        WorkSpace workSpace = optional.get();
        workSpace.setName(workspaceDto.getName());
        workSpace.setColor(workspaceDto.getColor());
        workSpace.setAvatar(attachment);
        WorkSpace editedWorkspace = workspaceRepository.save(workSpace);
        return convertToDto.editedResponseWorkspace(editedWorkspace);
    }


    /**
     * This method is used for deleting Workspace data in db if not found Workspace data\
     * throw new WorkspaceNotFoundException
     *
     * @param id       Long
     * @param language Language
     * @return DeleteWorkspaceDto
     */
    public DeleteWorkSpaceDto deleteWorkspace(Long id, Language language) {
        Optional<WorkSpace> optional = workspaceRepository.findById(id);
        if (optional.isEmpty()) {
            throw new WorkspaceNotFoundException(resourceBundleService.getMessage("workspace.not.found", language));
        }
        workspaceRepository.delete(optional.get());
        return new DeleteWorkSpaceDto(204, optional.get().getName() + " successfully deleted");
    }


    /**
     * This method is used for viewing all the workspace data if not founded
     * throw new WorkspaceNotFoundException
     *
     * @param language Language
     * @return List<ResponseWorkspaceList></>
     */
    public List<ResponseWorkspaceList> getAllWorkspace(Language language) {
        List<WorkSpace> list = workspaceRepository.findAll();
        if (list.isEmpty()) {
            throw new WorkspaceNotFoundException(resourceBundleService.getMessage("workspace.not.found", language));
        }
        List<ResponseWorkspaceList> lists = new ArrayList<>();
        for (WorkSpace workSpace : list) {
            ResponseWorkspaceList dto = new ResponseWorkspaceList();
            dto.setId(workSpace.getId());
            dto.setName(workSpace.getName());
            dto.setColor(workSpace.getColor());
            dto.setInitialLetter(workSpace.getInitialLetter());
            lists.add(dto);
        }

        return lists;
    }


    /**
     * This method is used for viewing workspace data by id if not founded
     * throw  new WorkspaceNotFoundException
     *
     * @param id       Long
     * @param language Language
     * @return ResponseWorkspaceList
     */
    public ResponseWorkspaceList getWorkspaceById(Long id, Language language) {
        Optional<WorkSpace> optional = workspaceRepository.findById(id);
        if (optional.isEmpty()) {
            throw new WorkspaceNotFoundException(resourceBundleService.getMessage("workspace.not.found", language));
        }
        return convertToDto.convertResponseWorkspace(optional.get());
    }
}
