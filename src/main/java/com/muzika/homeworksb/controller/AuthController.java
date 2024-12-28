package com.muzika.homeworksb.controller;

import com.muzika.homeworksb.dto.UserLoginRequestDto;
import com.muzika.homeworksb.dto.UserLoginResponseDto;
import com.muzika.homeworksb.dto.UserRegistrationRequestDto;
import com.muzika.homeworksb.dto.UserRegistrationResponseDto;
import com.muzika.homeworksb.exception.RegistrationException;
import com.muzika.homeworksb.security.AuthenticationService;
import com.muzika.homeworksb.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/register")
    public UserRegistrationResponseDto register(
        @Valid @RequestBody UserRegistrationRequestDto request
    ) throws RegistrationException {
        return userService.register(request);
    }
}
