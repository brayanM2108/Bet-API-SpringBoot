package com.melo.bets.infrastructure.persistence.mapper;


import com.melo.bets.domain.dto.user.LoginDto;
import com.melo.bets.domain.dto.user.UserDto;
import com.melo.bets.domain.dto.user.UserRegisterDto;
import com.melo.bets.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserRoleMapper.class})
public interface UserMapper {


    UserDto toUserDto(UserEntity user);

    LoginDto toLoginDto(UserEntity user);

    UserRegisterDto toUserRegisterDto(UserEntity user);

    List<UserDto> toUserDtoList(List<UserEntity> users);


    @Mapping(target = "password", ignore = true)
    @Mapping(target = "document", ignore = true)
    UserEntity toUserDTOEntity(UserDto userDto);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "balance", ignore = true)
    UserEntity toUserRegisterDto(UserRegisterDto user);




}
