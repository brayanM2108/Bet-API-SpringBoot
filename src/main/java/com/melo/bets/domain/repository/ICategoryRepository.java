package com.melo.bets.domain.repository;


import com.melo.bets.infrastructure.persistence.entity.CategoryEntity;

import java.util.Optional;
import java.util.UUID;

public interface ICategoryRepository {

    Optional <CategoryEntity> findById (UUID id);
}
