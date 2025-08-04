package com.melo.bets.persistence.mapper;

import com.melo.bets.domain.User;
import com.melo.bets.persistence.entity.UserEntity;
import com.melo.bets.persistence.entity.UserRoleEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserRoleMapper.class})
public interface UserMapper {

    User toUser(UserEntity userEntity);

    List<User> toUserList(List<UserEntity> userEntities);

    @InheritInverseConfiguration
    UserEntity toUserEntity(User user);



}
