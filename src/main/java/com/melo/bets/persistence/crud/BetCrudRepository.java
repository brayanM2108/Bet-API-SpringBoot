package com.melo.bets.persistence.crud;

import com.melo.bets.persistence.entity.BetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BetCrudRepository extends JpaRepository<BetEntity, UUID> {


}
