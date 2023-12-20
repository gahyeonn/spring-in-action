package com.gahyeonn.tacocloud.web;

import com.gahyeonn.tacocloud.Order;
import com.gahyeonn.tacocloud.data.OrderRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j //컴파일 시에 SLF4J Logger 객체 생성
@Controller
@RequestMapping("/orders")
@SessionAttributes("order") //폼에서 제출된 Order 객체를 저장하기 위해 세션 유지
public class OrderController {
    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    //타코 주문 폼을 나타냄
    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    //타코 주문 제출 처리
    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
        //Order 객체가 제출된 폼 필드와 바인딩 됨

        if (errors.hasErrors()) {
            return "orderForm";
        }

        orderRepo.save(order);
        sessionStatus.setComplete(); //안해주면 이전 주문 정보 세션에 남아있음. => 세션 재설정 위해 필요

        return "redirect:/";
    }
}
