package com.gahyeonn.tacocloud;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Taco {
    private Long id;

    private Date createdAt;

    //null 값 허용X, "", " " 가능
    @NotNull

    //최소 5글자 이상
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    //최소 1개 이상 선택
    @Size(min = 1, message = "You must choose at lease 1 ingredient")
    private List<String> ingredients;
}
