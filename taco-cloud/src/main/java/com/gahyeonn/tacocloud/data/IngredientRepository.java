package com.gahyeonn.tacocloud.data;

import com.gahyeonn.tacocloud.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();

    Ingredient findById(String id);

    Ingredient save(Ingredient ingredient);
}
