package com.muzika.homeworksb.service.impl;

import com.muzika.homeworksb.dto.UserRegistrationRequestDto;
import com.muzika.homeworksb.dto.UserRegistrationResponseDto;
import com.muzika.homeworksb.enums.RoleEnum;
import com.muzika.homeworksb.exception.RegistrationException;
import com.muzika.homeworksb.mapper.UserMapper;
import com.muzika.homeworksb.model.User;
import com.muzika.homeworksb.model.UserRole;
import com.muzika.homeworksb.repository.UserRepository;
import com.muzika.homeworksb.repository.UserRoleRepository;
import com.muzika.homeworksb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRoleRepository userRoleRepository;

    @Override
    @Transactional(timeout = 1, rollbackFor = RegistrationException.class)
    public UserRegistrationResponseDto register(UserRegistrationRequestDto request) throws RegistrationException {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RegistrationException("Unable to complete registration.");
        }
        // All users register with role client.
        UserRole userRole = userRoleRepository.findByName(String.valueOf(RoleEnum.CLIENT))
            .orElseThrow(() -> new RegistrationException("Role " + RoleEnum.CLIENT + " not found"));

        User user = new User();
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setRoles(List.of(userRole));

        return userMapper.toUserResponse(userRepository.save(user));
    }
}
