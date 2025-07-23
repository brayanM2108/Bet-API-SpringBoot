package com.melo.bets.persistence.crud;

import com.melo.bets.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserCrudRepository extends CrudRepository<UserEntity, UUID> {

}
