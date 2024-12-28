package com.muzika.homeworksb.service;

import com.muzika.homeworksb.dto.UserRegistrationRequestDto;
import com.muzika.homeworksb.dto.UserRegistrationResponseDto;
import com.muzika.homeworksb.exception.RegistrationException;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
}
