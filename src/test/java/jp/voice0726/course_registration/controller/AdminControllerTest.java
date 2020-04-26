package jp.voice0726.course_registration.controller;

import jp.voice0726.course_registration.config.AppConfig;
import jp.voice0726.course_registration.dto.StudentIndexDto;
import jp.voice0726.course_registration.dto.StudentProfileDto;
import jp.voice0726.course_registration.entity.Course;
import jp.voice0726.course_registration.entity.Department;
import jp.voice0726.course_registration.entity.Enrollment;
import jp.voice0726.course_registration.entity.Student;
import jp.voice0726.course_registration.form.StudentAddForm;
import jp.voice0726.course_registration.helper.WithMockCustomUser;
import jp.voice0726.course_registration.service.DepartmentService;
import jp.voice0726.course_registration.service.StudentService;
import jp.voice0726.course_registration.service.impl.AdminUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
    DepartmentService departmentService;

    @MockBean
    AdminUserServiceImpl adminUserService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockCustomUser(role = "ROLE_ADMIN")
    void indexTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/index"));
    }

    @Test
    @WithMockCustomUser(role = "ROLE_ADMIN")
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
    @WithMockCustomUser(role = "ROLE_ADMIN")
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

        for (Student student : students) {
            when(studentService.getStudentById(any(Long.class))).thenReturn(student);
            ModelMapper modelMapper = new ModelMapper();
            StudentProfileDto dto = modelMapper.map(student, StudentProfileDto.class);
            mockMvc.perform(MockMvcRequestBuilders.get("/admin/students/" + student.getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("admin/students/profile"))
                    .andExpect(model().attribute("profile", dto));
        }

    }

    @Test
    @WithMockCustomUser(role = "ROLE_ADMIN")
    void addStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/students/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/students/add"))
                .andExpect(model().attribute("studentForm", new StudentAddForm()));
    }

    @Test
    @WithMockCustomUser(role = "ROLE_ADMIN")
    void addStudentExec() throws Exception {

        String studentId = "111111L";
        String familyName = "Family";
        String givenName = "Give";
        String initialPassword = "123456";
        int admissionYear = 2020;
        long departmentId = 1L;

        mockMvc.perform(
                MockMvcRequestBuilders.post("/admin/students/add")
                        .with(csrf())
                        .param("studentId", studentId)
                        .param("familyName", familyName)
                        .param("givenName", givenName)
                        .param("initialPassword", initialPassword)
                        .param("admissionYear", String.valueOf(admissionYear))
                        .param("departmentId", String.valueOf(departmentId))
        ).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/students/"));

        Student expected = createStudent(
                studentId, familyName, givenName, initialPassword, admissionYear, departmentId
        );

        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(studentService).addStudent(captor.capture());
        assertThat(captor.getValue()).usingRecursiveComparison().ignoringFields("department")
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @WithMockCustomUser(role = "ROLE_ADMIN")
    @CsvSource({
            "'',aaa, aaa, aaaa, 2020, 2, studentId, 学籍番号を入力してください。",
            "111111T,'', aaa, aaaa, 2020, 2, familyName, 姓を入力してください。",
            "111111T,aa, '', aaaa, 2020, 2, givenName, 名を入力してください。",
            "111111T,aa, aa, '', 2020, 2, initialPassword, 初期パスワードを入力してください。",
            "111111T,aa, aa, aaa, , 2, admissionYear, 入学年を入力してください。",
            "111111T,aa, aa, aaa, 2020, 0, departmentId, 所属学部を選択してください。"
    })
    void addStudentExecWithError(
            String param1, String param2, String param3, String param4, String param5, String param6, String invalidFieldName, String errorMsg
    ) throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/admin/students/add")
                        .with(csrf())
                        .param("studentId", param1)
                        .param("familyName", param2)
                        .param("givenName", param3)
                        .param("initialPassword", param4)
                        .param("admissionYear", param5)
                        .param("departmentId", param6)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/students/add"))
                .andReturn();

        verify(studentService, times(0)).addStudent(any());

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        BindingResult errors = (BindingResult) model.get("org.springframework.validation.BindingResult.studentForm");
        String message = errors.getFieldError(invalidFieldName).getDefaultMessage();
        assertThat(message).isEqualTo(errorMsg);
    }

    private Student createStudent(String param1, String param2, String param3, String param4, int i, long l) {
        Student expected = new Student();
        expected.setStudentId(param1);
        expected.setFamilyName(param2);
        expected.setGivenName(param3);
        expected.setPassword(param4);
        expected.setAdmissionYear(i);
        expected.setDepartmentId(l);
        expected.setDepartment(new Department());
        expected.getDepartment().setId(l);
        expected.getDepartment().setName("test");
        expected.getDepartment().setCreatedBy(0);
        expected.getDepartment().setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        expected.getDepartment().setUpdatedBy(0);
        expected.getDepartment().setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return expected;
    }
}
