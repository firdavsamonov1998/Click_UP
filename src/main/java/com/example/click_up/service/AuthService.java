package com.example.click_up.service;

import com.example.click_up.entity.UserEntity;
import com.example.click_up.enums.Language;
import com.example.click_up.enums.SystemRole;
import com.example.click_up.exception.UserNameExistException;
import com.example.click_up.payload.RegisterUserDto;
import com.example.click_up.payload.ResponseRegisterDto;
import com.example.click_up.repository.AuthRepository;
import com.example.click_up.util.ConvertToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService implements UserDetailsService {

    private final AuthRepository authRepository;
    private final ResourceBundleService resourceBundleService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender sender;
    private final ConvertToDto convertToDto;

    @Autowired
    public AuthService(AuthRepository authRepository, ResourceBundleService resourceBundleService, PasswordEncoder passwordEncoder, JavaMailSender sender, ConvertToDto convertToDto) {
        this.authRepository = authRepository;
        this.resourceBundleService = resourceBundleService;
        this.passwordEncoder = passwordEncoder;
        this.sender = sender;
        this.convertToDto = convertToDto;
    }


    public ResponseRegisterDto registerUser(RegisterUserDto registerUserDto, Language language) {

        if (authRepository.existsByUsername(registerUserDto.getUsername())) {
            throw new UserNameExistException(resourceBundleService.getMessage("username.exist", language));
        }


        UserEntity user = new UserEntity();
        user.setFullName(registerUserDto.getFullName());
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setRole(SystemRole.ROLE_USER);

        int code = new Random().nextInt(11111, 99999);
        user.setEmailCode(String.valueOf(code));
        authRepository.save(user);
        sendEmail(registerUserDto.getUsername(), user.getEmailCode());


        return convertToDto.registerDto(user);
    }

    public void sendEmail(String toEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("firdavsprogramer@gmail.com");
            mailMessage.setTo(toEmail);
            mailMessage.setSubject("Accountni tasdiqlash");
            mailMessage.setText(emailCode);
            sender.send(mailMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
