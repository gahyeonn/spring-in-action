package com.gahyeonn.tacocloud;

import com.gahyeonn.tacocloud.web.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/* 스프링 MVC 애플리케이션의 형태로 테스트가 실행되도록 함
   HomeController가 스프링 MVC에 등록되어 스프링 MVC에 웹 요청을 보낼 수 있음
   스프링 MVC를 테스트하기 위한 스프링 지원 설정 - 실제 서버 실행X, 모의(mocking) 매커니즘 사용 위해 테스트 클래스에 MockMvc 객체 주입
*/
@WebMvcTest(HomeController.class) //HomeContoller의 웹 페이지 테스트
public class HomeControllerTest {

    @Autowired
    private  MockMvc mockMvc; //MockMvc를 주입한다.

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/")) //Get /를 수행한다.
                .andExpect(status().isOk()) //Http 200이 되어야 한다.
                .andExpect(view().name("home")) //home 뷰가 있어야 한다.
                .andExpect(content().string(containsString("Welcome to..."))); //콘텐츠에 "Welcome to..."가 포암되야 한다.
    }
}
