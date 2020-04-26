package jp.voice0726.course_registration.controller;

import jp.voice0726.course_registration.dto.StudentIndexDto;
import jp.voice0726.course_registration.dto.StudentProfileDto;
import jp.voice0726.course_registration.entity.Student;
import jp.voice0726.course_registration.form.StudentAddForm;
import jp.voice0726.course_registration.form.StudentEditForm;
import jp.voice0726.course_registration.service.DepartmentService;
import jp.voice0726.course_registration.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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
    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;

    @ModelAttribute("departments")
    public Map<Long, String> getDepartmentMap() {
        return departmentService.findAllMap();
    }

    @GetMapping
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("admin/index");
        return mav;
    }

    @GetMapping("/students/")
    public ModelAndView studentIndex(ModelAndView mav, Pageable pageable) {
        Page<Student> students = studentService.getList(pageable);
        Page<StudentIndexDto> dtos = convertToIndexDto(students);
        mav.addObject("students", dtos);
        mav.setViewName("admin/students/index");
        return mav;
    }

    @GetMapping("/students/{studentId}")
    public ModelAndView profile(ModelAndView mav, @PathVariable("studentId") long studentId) {
        Student profile = studentService.getStudentById(studentId);
        StudentProfileDto dto = convertToProfileDto(profile);
        mav.addObject("profile", dto);
        mav.setViewName("admin/students/profile");
        return mav;
    }

    @GetMapping("/students/add")
    public ModelAndView addStudent(ModelAndView mav, @ModelAttribute StudentAddForm studentAddForm) {
        mav.setViewName("admin/students/add");
        return mav;
    }

    @PostMapping("/students/add")
    public ModelAndView addStudentExec(
            ModelAndView mav,
            @Validated @ModelAttribute StudentAddForm studentAddForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            mav.setViewName("admin/students/add");
        } else {
            Student stu = modelMapper.map(studentAddForm, Student.class);
            studentService.addStudent(stu);
            mav.setViewName("redirect:/admin/students/");
        }

        return mav;
    }

    @GetMapping("/students/edit/{id}")
    public ModelAndView editStudent(ModelAndView mav, @PathVariable long id) {
        Student student = studentService.getStudentById(id);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        StudentEditForm studentEditForm = modelMapper.map(student, StudentEditForm.class);
        mav.addObject("studentEditForm", studentEditForm);
        mav.setViewName("admin/students/edit");
        return mav;
    }

    @PostMapping("/students/edit/{id}")
    public ModelAndView editStudentExec(
            ModelAndView mav,
            @Validated @ModelAttribute StudentEditForm studentEditForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            mav.setViewName("admin/students/edit");
        } else {
            Student stu = convertToEntityForUpdate(studentEditForm);
            studentService.updateStudent(stu);
            mav.setViewName("redirect:/admin/students/");
        }

        return mav;
    }


    private Page<StudentIndexDto> convertToIndexDto(Page<Student> studentPage) {
        return studentPage.map(e -> modelMapper.map(e, StudentIndexDto.class));
    }

    private StudentProfileDto convertToProfileDto(Student student) {
        return modelMapper.map(student, StudentProfileDto.class);
    }

    private Student convertToEntityForUpdate(StudentEditForm form) {
        Student stu = studentService.getStudentById(form.getId());
        modelMapper.map(form, stu);
        return stu;
    }
}
