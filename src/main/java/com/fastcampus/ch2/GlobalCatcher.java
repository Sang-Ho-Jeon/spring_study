package com.fastcampus.ch2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice("com.fastcampus.ch3") // 지정된 패키지에서 발생한 예외만 처리
//@ControllerAdvice // 모든 패키지에 적용
public class GlobalCatcher {
    @ExceptionHandler(Exception.class)
    public String catcher(Exception ex, Model m) {
        m.addAttribute("ex", ex);
        return "error";
    }

    @ExceptionHandler({NullPointerException.class, FileNotFoundException.class})
    public String catcher2(Exception ex, Model m) {
        m.addAttribute("ex", ex);
        return "error";
    }
}
