package com.gahyeonn.tacocloud.data;

import com.gahyeonn.tacocloud.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/* @Repository
    @Component에서 특화된 데이터 액세스 관련 애노테이션
    => 스프링 컴포넌트 검색에서 이 클래스를 찾아 스프링 애플리케이션 컨텍스트의 빈으로 생성

    스테레오타입(stereotype) 애노테이션: 스프링에서 주로 사용하는 역할 그룹을 나타내는 애노테이션
    ex) @Component, @Controller, @Service, @Repository
*/
@Repository
public class JdbcIngredientRepository implements IngredientRepository {
    private JdbcTemplate jdbc;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    //객체가 저장된 컬렉션 반환
    @Override
    public Iterable<Ingredient> findAll() {
        /* query()
            객체가 저장된 컬렉션 반환
            - 첫 번째 인자: 쿼리 수행 SQL
            - 두 번째 인자: RowMapper 인터페이스를 구현한 메서드 => 쿼리로 생성된 결과 세트(ResultSet)의 행(Row) 개수만큼 호출
                          결과 세트의 모든 행을 각각 객체로 생성하고 List에 저장한 후 반환
            - 세 번째 인자: 해당 쿼리에서 요구하는 매개변수들의 내역 => 없으면 생략
         */
        return jdbc.query("select id, name, type from Ingredient", this::mapRowToIngredient);
    }

    @Override
    public Ingredient findById(String id) {
        /* queryForObject()
            하나의 객체만 반환하는 경우
            그 외는 query()와 동일
         */
        return jdbc.queryForObject(
                "select id, name, type form Ingredient where id=?", this::mapRowToIngredient, id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        //update(): 데이터베이스에 데이터를 추가하거나 변경하는 어떤 쿼리에도 사용 가능
        jdbc.update("insert into Ingredient (id, name, type) values (?, ?, ?) ",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());

        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }
}
