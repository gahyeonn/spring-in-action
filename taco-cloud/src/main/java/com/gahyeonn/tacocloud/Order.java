package com.gahyeonn.tacocloud;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

@Data
public class Order {

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
}
