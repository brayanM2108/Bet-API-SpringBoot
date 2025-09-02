package com.melo.bets.infrastructure.persistence.crud;

import com.melo.bets.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CategoryCrudRepository extends CrudRepository<CategoryEntity, UUID> {
}
