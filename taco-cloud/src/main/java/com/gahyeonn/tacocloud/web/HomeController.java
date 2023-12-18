package com.gahyeonn.tacocloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //지정된 클래스가 컴포넌트로 식별되게 하는 것이 주 목적
public class HomeController {

    @GetMapping("/") //루트 경로인 /의 웹 요청을 처리한다.
    public String home() {
        return "home"; //뷰 이름을 반환한다.
    }
}
