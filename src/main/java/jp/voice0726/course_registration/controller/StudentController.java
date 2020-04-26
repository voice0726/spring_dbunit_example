package jp.voice0726.course_registration.controller;

import jp.voice0726.course_registration.dto.StudentIndexDto;
import jp.voice0726.course_registration.dto.StudentProfileDto;
import jp.voice0726.course_registration.entity.Student;
import jp.voice0726.course_registration.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students/")
public class StudentController {

    private final StudentService studentService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ModelAndView index(ModelAndView mav, Pageable pageable) {
        Page<Student> students = studentService.getList(pageable);
        Page<StudentIndexDto> dtos = convertToDto(students);
        mav.addObject("students", dtos);
        mav.setViewName("students/index");
        return mav;
    }

    @GetMapping("/{studentId}")
    public ModelAndView profile(ModelAndView mav, @PathVariable("studentId") long studentId) {
        Student profile = studentService.getStudentById(studentId);
        StudentProfileDto profileDto = convertToDto(profile);
        mav.addObject("profile", profileDto);
        mav.setViewName("students/profile");
        return mav;
    }

    private Page<StudentIndexDto> convertToDto(Page<Student> studentPage) {
        return studentPage.map(e -> modelMapper.map(e, StudentIndexDto.class));
    }

    private StudentProfileDto convertToDto(Student student) {
        return modelMapper.map(student, StudentProfileDto.class);
    }

}
