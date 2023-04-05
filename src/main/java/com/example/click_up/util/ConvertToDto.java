package com.example.click_up.util;

import com.example.click_up.entity.UserEntity;
import com.example.click_up.entity.WorkSpace;
import com.example.click_up.payload.EditedResponseDto;
import com.example.click_up.payload.ResponseRegisterDto;
import com.example.click_up.payload.ResponseWorkspaceDto;
import com.example.click_up.payload.ResponseWorkspaceList;
import org.springframework.stereotype.Component;

@Component
public class ConvertToDto {

    public ResponseRegisterDto registerDto(UserEntity user) {
        ResponseRegisterDto registerDto = new ResponseRegisterDto();
        registerDto.setEmail(user.getUsername());
        registerDto.setFullName(user.getFullName());
        registerDto.setSuccess(true);
        registerDto.setStatusCode(201);
        registerDto.setMessage("Emailingizga xabar yuborildi iltimos tasdiqlang");
        return registerDto;
    }


    public ResponseWorkspaceDto responseWorkspaceDto(WorkSpace workspaceDto) {
        return new ResponseWorkspaceDto(
                201, workspaceDto.getName() + " created successfully"
        );
    }

    public EditedResponseDto editedResponseWorkspace(WorkSpace editedWorkspace) {
        return new EditedResponseDto(204,
                editedWorkspace.getName() + " Successfully edited");

    }

    public ResponseWorkspaceList convertResponseWorkspace(WorkSpace workSpace) {

        ResponseWorkspaceList list = new ResponseWorkspaceList();
        list.setName(workSpace.getName());
        list.setColor(workSpace.getColor());
        list.setInitialLetter(workSpace.getInitialLetter());
        list.setId(workSpace.getId());
        return list;
    }
}
