package jp.voice0726.spring_junit_example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/faculties/")
public class FacultyController {

    @GetMapping
    public ModelAndView index(ModelAndView mav, Pageable pageable) {
        return mav;
    }
}
