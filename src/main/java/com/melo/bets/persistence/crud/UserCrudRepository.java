package com.melo.bets.persistence.crud;

import com.melo.bets.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserCrudRepository extends JpaRepository<UserEntity, UUID> {

}
