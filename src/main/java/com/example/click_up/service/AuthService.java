package com.example.click_up.service;

import com.example.click_up.enums.Language;
import com.example.click_up.exception.UserNameExistException;
import com.example.click_up.payload.RegisterUserDto;
import com.example.click_up.payload.ResponseRegisterDto;
import com.example.click_up.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final AuthRepository authRepository;
    private final ResourceBundleService resourceBundleService;

    @Autowired
    public AuthService(AuthRepository authRepository, ResourceBundleService resourceBundleService) {
        this.authRepository = authRepository;
        this.resourceBundleService = resourceBundleService;
    }


    public ResponseRegisterDto registerUser(RegisterUserDto registerUserDto, Language language) {


        if (authRepository.existsByUsername(registerUserDto.getUsername())) {
            throw new UserNameExistException(resourceBundleService.getMessage("username.exist", language));
        }


        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
