package com.melo.bets.infrastructure.persistence;

import com.melo.bets.domain.exception.CategoryNotExistException;
import com.melo.bets.domain.repository.ICategoryRepository;
import com.melo.bets.infrastructure.persistence.crud.CategoryCrudRepository;
import com.melo.bets.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CategoryRepositoryImpl implements ICategoryRepository {

    private final CategoryCrudRepository categoryCrudRepository;

    public CategoryRepositoryImpl(CategoryCrudRepository categoryCrudRepository) {
        this.categoryCrudRepository = categoryCrudRepository;
    }

    @Override
    public Optional<CategoryEntity> findById(UUID id) {
        Optional<CategoryEntity> category = categoryCrudRepository.findById(id);
        if (category.isEmpty()) {
            throw new CategoryNotExistException(id);
        }
        return category;
    }
}
