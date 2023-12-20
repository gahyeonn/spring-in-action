package com.gahyeonn.tacocloud;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/* @NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
    Entity 클래스는 매개변수가 없는 생성자의 접근 레벨이 public 또는 protected로 해야 한다.
    JPA에서는 개체가 인자없는 생성자를 가져야 함.
    AccessLevel.PRIVATE의 경우, 에러 발생 가능
    해당 개체에는 초기화가 필요한 final 속성들이 있기 때문에 force = true로 사용
        Lombok이 자동 생성한 생성자에서 그 속성들을 null로 설정

     @Data가 인자 있는 생성자를 자동으로 추가하는데, @NoArgsConstructor가 지정되면 그런 생정자 제거
     => @RequiredArgsConstructor를 추가해서 인자 있는 생성자를 여전히 가질 수 있게 함.
 */
@Data //인자가 있는 생성자 자동 추가
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
@Entity //JPA 개체로 선언할 때 필수
public class Ingredient {
    @Id //entity를 고유하게 식별한다는 표시
    private final String id;

    private final String name;

    private final Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
