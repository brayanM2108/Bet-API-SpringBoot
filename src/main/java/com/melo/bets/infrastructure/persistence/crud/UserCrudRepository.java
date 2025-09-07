package com.melo.bets.infrastructure.persistence.crud;



import com.melo.bets.domain.dto.user.UserBalanceDto;
import com.melo.bets.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface UserCrudRepository extends JpaRepository<UserEntity, UUID> {


    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Query(value = """
SELECT new com.melo.bets.domain.dto.user.UserBalanceDto
(u.id, u.balance)
FROM UserEntity u WHERE u.id = :userId
""")
    Optional <UserBalanceDto> findBalanceByUserId(UUID userId);

    @Modifying
    @Query("UPDATE UserEntity u SET u.balance = :balance WHERE u.id = :userId")
    void updateBalance(@Param("userId") UUID userId, @Param("balance") BigDecimal balance);

    boolean existsByDocument(@Param("document") String document);
}
