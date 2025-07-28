package com.melo.bets.persistence.mapper;

import com.melo.bets.domain.Category;
import com.melo.bets.persistence.entity.CategoryEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {


    Category toCategory(CategoryEntity entity);

    @InheritInverseConfiguration
    CategoryEntity toCategoryEntity(Category category);

    List<Category> toCategoryList(List<CategoryEntity> entities);
}
