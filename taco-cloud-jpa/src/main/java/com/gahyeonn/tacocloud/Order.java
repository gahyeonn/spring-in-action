package com.gahyeonn.tacocloud;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* Serializable interface
    객체 직렬화 할 수 있게 해주는 인터페이스
        직렬화: 자바 시스템 내부에서 사용되는 객체 또는 데이터를 외부의 자바 시스템에서도 사용할 수 있도록 바이트(byte) 형태로
                데이터 변환하는 기술과 바이트로 변환된 데이터를 다시 객체로 변환하는 기술(역직렬화)
    장점: 복잡한 데이터 구조의 클래스의 객체라도 직렬화 기본 조건만 지키면 큰 작업 없이 바로 직렬화 가능
    사용할 때: JVM의 메모리에서만 상주되어있는 객체 데이터를 그대로 영속화(Persistence)가 필요할 때 사용
 */
@Data
@Entity
@Table(name = "Taco_Order") //Order 객체가 디비의 지정된 Taco_Order 테이블에 저장되어야 한다, 지정안하면 JPA가 Order 테이블 만들어서 저장
public class Order implements Serializable {
    //Serializable 인터페이스를 구현한 클래스에서는 serialVersionUID 필수 선언 필요
    //객체의 직렬화와 역직렬화의 호환성을 보장하고, 객체의 동일성을 확인하기 위해 선언
    private static final long serialVersionID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date placedAt;

    //null, "", " " 허용X
    @NotBlank(message = "Name is required")
    private String deliveryName;

    @NotBlank(message = "Street is required")
    private String deliveryStreet;

    @NotBlank(message = "City is required")
    private String deliveryCity;

    @NotBlank(message = "State is required")
    private String deliveryState;

    @NotBlank(message = "Zip is required")
    private String deliveryZip;

    //Luhn(룬) 알고리즘 검사에 합격한 유효한 신용 카드 번호인지 검사 => 사용자의 입력 식수나 고의적인 악성 데이터 방지
    //입력된 신용 카드 번호가 실제로 존재하는 것인지, 대금 지불에 사용될 수 있는지 검사는 불가 => 금융망 연결 필요
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    //정규 표현식 지정해서 값이 해당 형식을 따르는지 검사
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    //입력 값이 세 자리 숫자인지 검사
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    @ManyToMany
    private List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco design) {
        this.tacos.add(design);
    }

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }
}
