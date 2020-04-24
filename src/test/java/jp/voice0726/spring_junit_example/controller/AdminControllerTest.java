package jp.voice0726.spring_junit_example.controller;

import jp.voice0726.spring_junit_example.config.AppConfig;
import jp.voice0726.spring_junit_example.dto.StudentIndexDto;
import jp.voice0726.spring_junit_example.dto.StudentProfileDto;
import jp.voice0726.spring_junit_example.entity.Course;
import jp.voice0726.spring_junit_example.entity.Enrollment;
import jp.voice0726.spring_junit_example.entity.Student;
import jp.voice0726.spring_junit_example.helper.WithMockCustomUser;
import jp.voice0726.spring_junit_example.service.StudentService;
import jp.voice0726.spring_junit_example.service.impl.AdminUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by akinori on 2020/04/23
 *
 * @author akinori
 */
@WebMvcTest(
        controllers = AdminController.class,
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {AppConfig.class}
        )
)
public class AdminControllerTest {
    @MockBean
    StudentService studentService;

    @MockBean
    AdminUserServiceImpl adminUserService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockCustomUser(role = "ADMIN")
    void indexTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/index"));
    }

    @Test
    @WithMockCustomUser(role = "ADMIN")
    void studentIndexTest() throws Exception {

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

        when(studentService.getList(any(Pageable.class))).thenReturn(new PageImpl<>(returned));
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/students/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/students/index"))
                .andExpect(model().attribute("students", expected));
    }


    @Test
    @WithMockCustomUser(role = "ADMIN")
    void profileTest() throws Exception {
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

        List<StudentProfileDto> expectedList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            StudentProfileDto dto = new StudentProfileDto();
            dto.setId(1L);
            dto.setGivenName("test");
            dto.setFamilyName("test");
            dto.setAdmissionYear(2000);
            dto.setEnrollments(enrollments);
            expectedList.add(dto);
        }

        for (StudentProfileDto dto : expectedList) {
            when(studentService.getProfileById(any(Long.class))).thenReturn(dto);
            mockMvc.perform(MockMvcRequestBuilders.get("/admin/students/" + dto.getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("admin/students/profile"))
                    .andExpect(model().attribute("profile", dto));
        }

    }
}
