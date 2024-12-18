package com.fastcampus.ch2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping("/login")
    public String loginForm(Model m) {
        return "loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
//        public String logout(HttpSession session) { // 이렇게 쓰면 스프링이 세션 객체 자동 주입
        // 1. 세션 객체 얻어오기
        HttpSession session = request.getSession();
        // 2. 세션 삭제
        session.invalidate();

        // 3. 로그인 화면으로 이동
        return "redirect:/login/login";
    }

    @PostMapping("login")
    public String login(String toURL, String id, String pwd, boolean rememberId, HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("POST[login/login] = " + toURL);
        // toURL이 null이거나 빈 문자열이면 홈으로
        toURL = (toURL == null || toURL =="") ? "/" : toURL;
        // 1. id와 pwd 확인
        if(!loginCheck(id,pwd)) {
            // 2-1 일치하지 않으면, loginForm으로 이동
            String msg = URLEncoder.encode("id 또는 pwd가 일치하지 않습니다.", "utf-8");

            return "redirect:/login/login?msg=" + msg +"&toURL="+toURL;
        }
        // 2-2. id와 pwd가 일치하면,

        // 세션 객체를 얻어오기
        HttpSession session = request.getSession();
        // 세션 객체에 id를 저장
        session.setAttribute("id", id);

        if (rememberId) {
            // 1. 쿠키를 생성
            Cookie cookie = new Cookie("id", id);
            // 2. 응답에 저장
            response.addCookie(cookie);
        } else { // 1. 쿠키를 생성
            Cookie cookie = new Cookie("id","");
            // 2. 쿠키를 삭제
            cookie.setMaxAge(0);
            // 3. 응답에 저장
            response.addCookie(cookie);
        }

        // 홈으로 이동
        return "redirect:"+toURL;
    }

    private boolean loginCheck(String id, String pwd) {
        return "asdf".equals(id) && "1234".equals(pwd);
    }
}
