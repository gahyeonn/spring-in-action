package com.gahyeonn.tacocloud.web;

import com.gahyeonn.tacocloud.Ingredient;
import com.gahyeonn.tacocloud.data.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
    Converter에 지정한 타입 변환이 필요할 때 convert() 자동 호출
    String 타입의 식자재 ID를 사용해서 디비에 저장된 특정 식자재 데이터를 읽은 후 Ingredient 객체로 변환하는데 사용

    Converter<String, Ingredient>
    String: 변환할 값의 타입
    Ingredient는 변환된 값의 타입
 */
@Component //스프링에 의해 자동 생성 및 주입되는 빈
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepository ingredientRepo;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        Optional<Ingredient> optionalIngredient = ingredientRepo.findById(id);
        return optionalIngredient.isPresent() ? optionalIngredient.get() : null;
    }
}
