package com.gahyeonn.tacocloud.data;

import com.gahyeonn.tacocloud.Taco;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private JdbcTemplate jdbc;

    //@Autowired => 여긴 왜 안해?
    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);

        for (String ingredientId : taco.getIngredients()) {
            saveIngredientToTaco(ingredientId, tacoId);
        }

        return taco;
    }

    //식자재 저장
    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());

        //PreparedStatementCreator 객체 생성
        var pscf = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values (?, ?) ", //SQL 명령
                Types.VARCHAR, Types.TIMESTAMP); //쿼리의 매개변수 타입

        //PreparedStatementCreatorFactory의 returnGeneratedKeys 값이 false인 경우 key 값 반환 안됨.
        pscf.setReturnGeneratedKeys(true);

        //PreparedStatementCreator 객체 생성
        var psc = pscf.newPreparedStatementCreator(
                Arrays.asList( //쿼리 매개변수 값을 인자로 전달
                        taco.getName(),
                        new Timestamp(taco.getCreatedAt().getTime()))
        );

        KeyHolder keyHolder = new GeneratedKeyHolder();

        //기존의 update()는 저장된 타코 ID를 얻을 수 없기에 아래 코드 사용
        //KeyHolder로부터 생성된 타코 ID를 제공 받음
        //KeyHolder를 사용하기 위해서는 PreparedStatementCreator도 생성해야 함
        jdbc.update(psc, keyHolder);

        return keyHolder.getKey().longValue(); //타코 ID 반환
    }

    // 타코와 삭자재의 연관 정보 저장
    private void saveIngredientToTaco(String ingredientId, long tacoId) {
        jdbc.update("insert into Taco_Ingredients (taco, ingredient) values (?, ?)", tacoId, ingredientId);
    }

}
