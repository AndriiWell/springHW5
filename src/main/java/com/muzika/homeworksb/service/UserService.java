package com.muzika.homeworksb.service;

import com.muzika.homeworksb.dto.UserRegistrationRequestDto;
import com.muzika.homeworksb.dto.UserRegistrationResponseDto;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto request) throws com.muzika.homeworksb.exception.RegistrationException;
}
