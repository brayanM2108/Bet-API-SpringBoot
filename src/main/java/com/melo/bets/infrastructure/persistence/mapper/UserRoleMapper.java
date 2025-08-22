package com.melo.bets.infrastructure.persistence.mapper;


import com.melo.bets.domain.dto.user.UserRole;
import com.melo.bets.infrastructure.persistence.entity.UserRoleEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {



    UserRole toUserRole(UserRoleEntity userRoleEntity);
    List<UserRole> toUserRoles(List<UserRoleEntity> userRoleEntities);

    @InheritInverseConfiguration
    UserRoleEntity toUserRoleEntity(UserRole userRole);
}
