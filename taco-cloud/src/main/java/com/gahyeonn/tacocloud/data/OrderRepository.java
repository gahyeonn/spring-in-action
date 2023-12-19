package com.gahyeonn.tacocloud.data;

import com.gahyeonn.tacocloud.Order;

public interface OrderRepository {
    Order save(Order order);
}
