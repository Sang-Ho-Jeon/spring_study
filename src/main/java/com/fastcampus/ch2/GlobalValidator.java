package com.fastcampus.ch2;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class GlobalValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
//        return User.class.isAssignableFrom(clazz); // clazz가 User 또는 그 자손인지 확인
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("GlobalValidator.validate() is called");
        /*
            id 입력값 데이터 검증
            (1) target을 User로 형변환
            (2) User객체의 id 받아오기
            (3) 아이디가 빈문자열이거나 공백이면
                ValidationUtils.rejectIfEmptyOrWhiteSpace(errors,필드명, 에러코드) 메서드로 에러 저장
            (4) id가 null이거나 5자리보다 짧거나 12자리보다 길면
                errors.rejectValue(필드명, 에러코드) 메서드로 해당 필드에 대한 에러 저장
         */

        User user = (User) target;
        String id = user.getId();

//        if (id == null || "".equals(id.trim())) {
//            errors.rejectValue("id","required");
//        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "required");

        if (id == null || id.length() < 5 || id.length() > 12) {
            errors.rejectValue("id", "invalidLength", new String[]{"", "5", "12"}, null);
        }
    }
}
