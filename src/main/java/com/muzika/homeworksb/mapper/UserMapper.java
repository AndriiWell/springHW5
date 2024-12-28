package com.muzika.homeworksb.mapper;

import com.muzika.homeworksb.config.MapperConfig;
import com.muzika.homeworksb.dto.UserRegistrationResponseDto;
import com.muzika.homeworksb.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserRegistrationResponseDto toUserResponse(User user);
}
