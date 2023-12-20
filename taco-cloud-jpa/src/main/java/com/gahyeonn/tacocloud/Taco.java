package com.gahyeonn.tacocloud;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //디비가 자동으로 생성해주는 ID 값 사용
    private Long id;

    private Date createdAt;

    //null 값 허용X, "", " " 가능
    @NotNull

    //최소 5글자 이상
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @ManyToMany(targetEntity = Ingredient.class) //하나의 Taco 객체는 많은 Ingredient 객체 가질 수 있고, 반대도 성립하는 관계
    //최소 1개 이상 선택
    @Size(min = 1, message = "You must choose at lease 1 ingredient")
    private List<Ingredient> ingredients;

    @PrePersist
        //엔티티가 영속화(디비 저장)되기 전에 실행
    void createdAt() {
        this.createdAt = new Date();
    }
}
