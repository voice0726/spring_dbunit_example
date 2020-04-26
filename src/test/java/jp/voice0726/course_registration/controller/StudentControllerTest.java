package jp.voice0726.course_registration.controller;

import jp.voice0726.course_registration.config.AppConfig;
import jp.voice0726.course_registration.dto.StudentIndexDto;
import jp.voice0726.course_registration.dto.StudentProfileDto;
import jp.voice0726.course_registration.entity.Course;
import jp.voice0726.course_registration.entity.Enrollment;
import jp.voice0726.course_registration.entity.Student;
import jp.voice0726.course_registration.service.StudentService;
import jp.voice0726.course_registration.service.impl.AdminUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(
        controllers = StudentController.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AppConfig.class)
)
class StudentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @MockBean
    AdminUserServiceImpl adminUserService;

    @Test
    @WithMockUser
    void index() throws Exception {

        List<StudentIndexDto> list = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            StudentIndexDto dto = new StudentIndexDto();
            dto.setId(i);
            dto.setFamilyName("test");
            dto.setGivenName("test" + i);
            dto.setAdmissionYear(2020);
            list.add(dto);
        }
        Page<StudentIndexDto> expected = new PageImpl<>(list);

        List<Student> returned = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            Student stu = new Student();
            stu.setId(i);
            stu.setFamilyName("test");
            stu.setGivenName("test" + i);
            stu.setAdmissionYear(2020);
            returned.add(stu);
        }

        when(studentService.getList(ArgumentMatchers.any(Pageable.class))).thenReturn(new PageImpl<>(returned));
        mockMvc.perform(MockMvcRequestBuilders.get("/students/"))
                .andDo(print())
                .andExpect(view().name("students/index"))
                .andExpect(model().attribute("students", expected));
    }


    @Test
    @WithMockUser
    void profile() throws Exception {
        List<Enrollment> enrollments = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            Course c = new Course();
            c.setCourseNum((short) 101);
            c.setCoursePrefix("ECO");
            c.setName("Introduction to Economics");
            c.setMaxEnroll(50);
            c.setId(1L);
            c.setInstructorId(1L);
            Enrollment en = new Enrollment();
            en.setCourse(c);
            enrollments.add(en);
        }

        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Student stu = new Student();
            stu.setId(1L);
            stu.setGivenName("test");
            stu.setFamilyName("test");
            stu.setAdmissionYear(2000);
            stu.setEnrollments(enrollments);
            students.add(stu);
        }

        for (Student stu : students) {
            when(studentService.getStudentById(ArgumentMatchers.any(Long.class))).thenReturn(stu);
            ModelMapper modelMapper = new ModelMapper();
            StudentProfileDto profile = modelMapper.map(stu, StudentProfileDto.class);
            mockMvc.perform(MockMvcRequestBuilders.get("/students/" + profile.getId()))
                    .andDo(print())
                    .andExpect(view().name("students/profile"))
                    .andExpect(model().attribute("profile", profile));
        }

    }
}
