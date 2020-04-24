package jp.voice0726.spring_junit_example.controller;

import jp.voice0726.spring_junit_example.dto.StudentIndexDto;
import jp.voice0726.spring_junit_example.dto.StudentProfileDto;
import jp.voice0726.spring_junit_example.entity.Student;
import jp.voice0726.spring_junit_example.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by akinori on 2020/04/23
 *
 * @author akinori
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class AdminController {

    private final StudentService studentService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("admin/index");
        return mav;
    }

    @GetMapping("/students/")
    public ModelAndView studentIndex(ModelAndView mav, Pageable pageable) {
        Page<Student> students = studentService.getList(pageable);
        Page<StudentIndexDto> dtos = convertToDto(students);
        mav.addObject("students", dtos);
        mav.setViewName("admin/students/index");
        return mav;
    }

    @GetMapping("/students/{studentId}")
    public ModelAndView profile(ModelAndView mav, @PathVariable("studentId") long studentId) {
        StudentProfileDto profile = studentService.getProfileById(studentId);
        mav.addObject("profile", profile);
        mav.setViewName("admin/students/profile");
        return mav;
    }


    private Page<StudentIndexDto> convertToDto(Page<Student> studentPage) {
        return studentPage.map(e -> modelMapper.map(e, StudentIndexDto.class));
    }
}
