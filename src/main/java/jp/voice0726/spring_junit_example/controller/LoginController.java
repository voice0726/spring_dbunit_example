package jp.voice0726.spring_junit_example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by akinori on 2020/04/23
 *
 * @author akinori
 */
@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin/login";
    }
}
