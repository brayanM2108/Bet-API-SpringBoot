package com.melo.bets.persistence.crud;

import com.melo.bets.persistence.entity.RaffleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RaffleCrudRepository extends JpaRepository<RaffleEntity, UUID> {
}
