package com.fastcampus.ch2;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller // ctrl+shift+o 자동 import
@RequestMapping("/register")
public class RegisterController {

    @InitBinder
    public void toDate(WebDataBinder binder) {
        ConversionService conversionService = binder.getConversionService();
//        System.out.println("conversionService= " + conversionService);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
        binder.registerCustomEditor(String[].class, "hobby", new StringArrayPropertyEditor("#"));

//        binder.setValidator(new UserValidator()); // UserValidator를 webDataBinder의 로컬 Validator로 등록

        // WebDataBinder에 등록된 validator 확인
        List<Validator> validators = binder.getValidators();
        System.out.println("validators=" + validators);
    }

    @RequestMapping(value="/add", method={RequestMethod.GET})
//	@GetMapping("/add")
    public String register() {
        return "registerForm"; // WEB-INF/views/registerForm.jsp
    }

    //	@RequestMapping(value="/save", method=RequestMethod.POST)
    @PostMapping("/add")  // 4.3부터
    public String save(@Valid User user, BindingResult result, Model m) throws Exception {
        System.out.println("result="+result);
        System.out.println("user="+user);

        /*
           user 객체 유효성 수동 검증 - validator를 직접 생성하고, validate()를 직접 호출
           1. userValidator 생성
           2. validate 메서드로 user객체 검증
           3. BindingResult에 에러가 있다면 registerForm으로 이동
         */
//        UserValidator userValidator = new UserValidator();
//        userValidator.validate(user, result); // BindingResult는 Errors 인터페이스의 자손

        // User 객체를 검증한 결과 에러가 있으면, registerForm을 이용해서 에러를 보여줘야 함
        if (result.hasErrors()) {
            return "registerForm";
        }

        // 1. 유효성 검사
//        if(!isValid(user)) {
//            String msg = URLEncoder.encode("id를 잘못입력하셨습니다.", "utf-8");
//
//            m.addAttribute("msg", msg);
//            return "forward:/register/add";
////			return "redirect:/register/add?msg="+msg; // URL재작성(rewriting)
//        }

        // 2. DB에 신규회원 정보를 저장
        return "registerInfo";
    }

    private boolean isValid(User user) {
        return true;
    }
}