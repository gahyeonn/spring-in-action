package com.gahyeonn.tacocloud.web;

import com.gahyeonn.tacocloud.Ingredient;
import com.gahyeonn.tacocloud.Ingredient.Type;
import com.gahyeonn.tacocloud.Order;
import com.gahyeonn.tacocloud.Taco;
import com.gahyeonn.tacocloud.data.IngredientRepository;
import com.gahyeonn.tacocloud.data.TacoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* @Slf4j
    Slf4j(Simple Logging Facade) Logger 생성
    컴파일 시에 Lombok에 제공
*/
/* @Controller
    해당 클래스가 컨트롤러로 식별되게 하며, 컴포넌트 검색을 해야 한다는 것을 나타냄
    스프링이 해당 클래스를 찾은 후 스프링 애플리케이션 컨텍스트의 빈으로 해당 클래스의 인스턴스를 자동 생성
*/
/* @SessionAttributes
    하나의 세션에서 생성되는 Taco 객체와 다르게, 주문은 다수의 HTTP 요청에 걸쳐 존재해야 한다.
    다수의 타코를 생성하고 그것들을 하나의 주문으로 추가할 수 있게 하기 위해서다.
    이때 클래스 수준의 @SessionAttributes를 주문과 같은 모델 객체에 지정하면 된다.
    그러면 세션에서 계속 보존되면서 다수의 요청에 걸쳐 사용될 수 있다.
*/
@Slf4j
@Controller
@RequestMapping("/design") //해당 컨트롤러가 처리하는 요청의 종류 나타냄 => 기본 경로 지정
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
    }

    /*
        '/design' 경로에 접속하면 실행되는 메서드
        뷰에 요청이 전달되기 전에 List에 저장된 식자재 데이터를 모델 객체(Model)에 넣는다.
     */
    /* Model
    컨트롤러와 데이터를 보여주는 뷰 사이에서 데이터를 운반하는 객체
    Model 객체의 속성에 있는 데이턴느 뷰가 알 수 있는 서블릿(servlet) 요청 속성들로 복사된다.
    */
    @GetMapping
    public String showDesignForm(Model model) {

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
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

    //Order 객체가 모델에 생성되도록 해준다.
    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }


    //타코를 디자인하는 사용자가 제출한 것을 처리
    //타코 디자인 폼이 제출될 때 이 폼의 필드는 processDesign() 인자로 전달되는 Taco 객체의 속성과 바인딩된다.
    /* @Valid
        해당 객체의 유효성 검사 수행(제출된 폼 데이터와 Taco 객체가 바인딩 된 후, 그리고 processDesign() 메서드의 코드가 실행되기 전)하라고 MVC에 알림
        어떤 검사라도 에러가 있으면 에러의 상세 내역이 Errors 객체에 저장되어 전달됨
    */
    /* @ModelAttribute
        해당 매개변수의 값이 모델로부터 전달되어야 한다는 것과 스프링 MVC가 이 매개변수에 요청 매개변수를 바인딩하지 않아야 한다는 것 나타냄
     */
    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {

        if (errors.hasErrors()) { //검사에 에러 있는지 확인
            return "design";
        }

        Taco saved = tacoRepo.save(design);

        //세션에 보존된 Order에 Taco 객체를 추가
        //사용자가 주문 폼에 입력을 완료하고 제출할 때까지 Order 객체는 세션에 남아있고 데이터베이스에 저장되지 않음
        order.addDesign(saved);

        return "redirect:/orders/current"; //redirect: 변경된 경로로 재접속하도록 리디렉션
    }
}
