package com.gahyeonn.tacocloud.data;

import com.gahyeonn.tacocloud.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    //특정 Zip(우편번호) 코드로 배달된 모든 주문 데이터 조회
    List<Order> findByDeliveryZip(String deliveryZip);

    //startDate~endDate 일자 범위 내에서 특정 Zip 코드로 배달된 모든 주문 조회 => read 다음의 Order(처리대상) 생략 가능
    List<Order> readOrderByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);
}
