package com.gahyeonn.tacocloud.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* WebConfig
    뷰 컨트롤러의 역할을 수행하는 구성 클래스
    뷰 컨트롤러란 모델 데이터나 사용자 입력을 처리하지 않고, 단순히 뷰에 요청을 전달하는 일만 하는 컨트롤러

    WebMvcConfigurer 인터페이스
       - 스프링 MVC를 구성하는 메서드를 정의
       - 인터페이스 임에도 불구하고, 정의된 모든 메서드의 기본 구현을 제공
            - 필요한 메서드만 선택해서 오버라이딩 하기
 */
@Configuration //구성 클래스 지정 => 웹, 데이터, 보안 등 각각의 종류별로 구성 클래스 만들어 사용하면 관리하기 편함
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /* addViewControllers()
            하나 이상의 뷰 컨트롤러를 등록하기 위해 사용할 수 있는 ViewControllerRegistry를 인자로 받음
            ViewControllerRegistration 객체를 반환
            ViewControllerRegistration 객체의 setViewName()을 통해 해당 경로의 요청이 전달되어야 하는 뷰를 지정
            => HomeController를 대체하는 코드 작성 가능
         */
        registry.addViewController("/").setViewName("home");
    }
}
