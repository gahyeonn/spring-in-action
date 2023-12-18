package com.gahyeonn.tacocloud;

import com.gahyeonn.tacocloud.Ingredient.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
    Slf4j(Simple Logging Facade) Logger 생성
    컴파일 시에 Lombok에 제공
 */
@Slf4j

/*
    해당 클래스가 컨트롤러로 식별되게 하며, 컴포넌트 검색을 해야 한다는 것을 나타냄
    스프링이 해당 클래스를 찾은 후 스프링 애플리케이션 컨텍스트의 빈으로 해당 클래스의 인스턴스를 자동 생성
 */
@Controller
@RequestMapping("/design") //해당 컨트롤러가 처리하는 요청의 종류 나타냄 => 기본 경로 지정
public class DesignTacoController {

    /*
        '/design' 경로에 접속하면 실행되는 메서드
        뷰에 요청이 전달되기 전에 List에 저장된 식자재 데이터를 모델 객체(Model)에 넣는다.
     */
    @GetMapping
    public String showDesignForm(Model model) {
        /* Model
            컨트롤러와 데이터를 보여주는 뷰 사이에서 데이터를 운반하는 객체
            Model 객체의 속성에 있는 데이턴느 뷰가 알 수 있는 서블릿(servlet) 요청 속성들로 복사된다.

         */

        List<Ingredient> ingredients = Arrays.asList(
            new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
            new Ingredient("CARN", "Carnitas", Type.PROTEIN),
            new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
            new Ingredient("LETC", "Lettuce", Type.VEGGIES),
            new Ingredient("CHED", "Cheddar", Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
            new Ingredient("SLSA", "Salsa", Type.SAUCE),
            new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        Type[] types = Ingredient.Type.values();
        for (Type type: types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        model.addAttribute("taco", new Taco());

        //데이터가 HTML로 작성되어 사용자의 웹 브라우저에 나타나게 하는 것이 뷰의 역할
        return "design"; //모델 데이터를 브라우저에 나타내는 데 사용될 뷰의 논리적인 이름
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
