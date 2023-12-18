package com.gahyeonn.tacocloud;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j //컴파일 시에 SLF4J Logger 객체 생성
@Controller
@RequestMapping("/orders")
public class OrderController {

    //타코 주문 폼을 나타냄
    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());

        return "orderForm";
    }

    //타코 주문 제출 처리
    @PostMapping
    public String processOrder(@Valid Order order, Errors errors) {
        //Order 객체가 제출된 폼 필드와 바인딩 됨

        if (errors.hasErrors()) {
            return "orderForm";
        }

        log.info("Order submitted: " + order);

        return "redirect:/";
    }
}
