package com.example.click_up.service;

import com.example.click_up.entity.UserEntity;
import com.example.click_up.enums.Language;
import com.example.click_up.enums.SystemRole;
import com.example.click_up.exception.UserNameExistException;
import com.example.click_up.exception.UserNotFoundException;
import com.example.click_up.exception.UsernameNotFoundExceptionn;
import com.example.click_up.payload.*;
import com.example.click_up.repository.AuthRepository;
import com.example.click_up.security.JwtProvider;
import com.example.click_up.util.ConvertToDto;
import com.example.click_up.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthService implements UserDetailsService {

    private final AuthRepository authRepository;
    private final ResourceBundleService resourceBundleService;
    private final JwtProvider jwtProvider;
    private final JavaMailSender sender;
    private final ConvertToDto convertToDto;

    @Autowired
    public AuthService(AuthRepository authRepository, ResourceBundleService resourceBundleService, JwtProvider jwtProvider, JavaMailSender sender, ConvertToDto convertToDto, UserEntity user) {
        this.authRepository = authRepository;
        this.resourceBundleService = resourceBundleService;
        this.jwtProvider = jwtProvider;
        this.sender = sender;
        this.convertToDto = convertToDto;

    }

    /**
     * This method for registration user
     * If the phone is already exist throw PhoneExistException and status 409
     * If the username is already exist throw UsernameExistException and status 409
     *
     * @param registerUserDto RegisterUserDto
     * @return ResponseRegisterDto
     */
    public ResponseRegisterDto registerUser(RegisterUserDto registerUserDto, Language language) {

        if (authRepository.existsByUsername(registerUserDto.getUsername())) {
            throw new UserNameExistException(resourceBundleService.getMessage("username.exist", language));
        }


        UserEntity user = new UserEntity();
        user.setFullName(registerUserDto.getFullName());
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(MD5.md5(registerUserDto.getPassword()));
        user.setRole(SystemRole.ROLE_USER);
        int code = new Random().nextInt(111111, 999999);
        user.setEmailCode(String.valueOf(code));
        authRepository.save(user);
        boolean email = sendEmail(registerUserDto.getUsername(), user.getEmailCode());
        return convertToDto.registerDto(user);
    }

    /**
     * This Method for sending Email
     *
     * @param toEmail   String
     * @param emailCode String
     */
    public boolean sendEmail(String toEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("firdavsprogramer@gmail.com");
            mailMessage.setTo(toEmail);
            mailMessage.setSubject("Accountni tasdiqlash");
            mailMessage.setText("Account ni tasdiqlash kodi : " + emailCode);
            sender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }





    /**
     * This method for Login To System
     * If the password or login is wrong is  throw UserNotFoundException and status 404
     *
     * @param loginDto LoginDto
     * @return ResponseLoginDto
     */
    public ResponseLoginDto loginToSystem(LoginDto loginDto, Language language) {
        Optional<UserEntity> optional = authRepository.
                findByUsernameAndPassword(loginDto.getUsername(), MD5.md5(loginDto.getPassword()));
        if (optional.isEmpty()) {
            throw new UserNotFoundException(resourceBundleService.getMessage("user.not.found", language));
        }
        String token = jwtProvider.encode(loginDto.getUsername());

        return new ResponseLoginDto("Success", true, token);
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

    public EmailResponse verifyEmail(String email, String emailCode, Language language) {
        Optional<UserEntity> optional = authRepository.findByUsername(email);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundExceptionn(resourceBundleService.getMessage("username.not.found", language));
        }

        UserEntity user = optional.get();
        String code = user.getEmailCode();
        if (code.equals(emailCode)) {
            user.setEnabled(true);
            user.setEmailCode(null);
            authRepository.save(user);
            return new EmailResponse("Success", true);
        }
        return new EmailResponse("UnSuccess ! EmailCode is Wrong !", false);
    }

    /**
     * This method is used for checking username is existed or not in db
     *
     * @param username String
     * @return UserEntity
     * throw UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundExceptionn(username));
    }
}
