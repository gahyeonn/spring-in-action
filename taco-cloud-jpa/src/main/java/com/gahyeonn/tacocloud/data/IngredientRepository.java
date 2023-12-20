package com.gahyeonn.tacocloud.data;

import com.gahyeonn.tacocloud.Ingredient;
import org.springframework.data.repository.CrudRepository;

/* CrudRepository 인터페이스
    디비의 CRUD 연산을 위한 많은 메서드가 선언되어 있다.
    => 애플리케이션이 시작될 때 스프링 데이터 JPA가 각 인터페이스 구현체를 자동으로 생성
    - 첫 번째 매개변수: 리퍼지터리에 저장되는 개체 타입
    - 두 번째 매개변수: 개체 ID 속성 타입

   JPARepository 인터페이스는 CurdRepository 인터페이스를 포함
    + 쿼리 메서드(Query Method), 페이징(Paging), 정렬(Sorting) 등의 기능을 사용 가능
 */
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
