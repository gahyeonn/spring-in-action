package com.gahyeonn.tacocloud.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gahyeonn.tacocloud.Order;
import com.gahyeonn.tacocloud.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JdbcOrderRepository implements OrderRepository{
    /* SimpleJdbcInsert
        데이터를 추가하는 2개의 메서드
        - execute(Map<String, Object> tmp)
        - excuteAndReturnKey(Map<String, Object> tmp)

        Map의 Key는 데이터가 추가되는 테이블의 열 이름과 대응, 값은 해당 열에 추가되는 값
     */
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id"); //Order 객체의 id 속성 값은 데이터베이스가 생성해 주는 것 사용

        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        for (Taco taco: tacos) {
            saveTacoToOrder(taco, orderId);
        }

        return order;
    }

    private long saveOrderDetails(Order order) {
        //원래 jackson은 Json 라이브러리인데 여기서 특이하게 사용한 것. => 속성 많을 때 매우 편리
        @SuppressWarnings("Unchecked")
        Map<String, Object> values = objectMapper.convertValue(order, Map.class); //Order 객체를 Map으로 변환
        values.put("placedAt", order.getPlacedAt()); //ObjectMapper는 Date 타입의 값을 long 타입으로 변환하기 때문에 재설정 필요

        //데이터 저장 후 디비에 생성된 ID가 Number 객체로 반환 -> long 타입 변환
        long orderId = orderInserter.executeAndReturnKey(values)
                .longValue();

        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }
}
