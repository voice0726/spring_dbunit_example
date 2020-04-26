package jp.voice0726.course_registration.service.impl;

import jp.voice0726.course_registration.config.AppConfig;
import jp.voice0726.course_registration.entity.Course;
import jp.voice0726.course_registration.entity.Enrollment;
import jp.voice0726.course_registration.entity.Student;
import jp.voice0726.course_registration.repository.StudentRepository;
import jp.voice0726.course_registration.service.StudentService;
import jp.voice0726.course_registration.specification.NameSearchSpecification;
import jp.voice0726.course_registration.user.LoginUser;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(StudentServiceImplTest.Config.class)
class StudentServiceImplTest {

    @Configuration
    @ComponentScan(
            basePackages = "jp.voice0726.course_registration",
            useDefaultFilters = false,
            includeFilters = @ComponentScan.Filter(
                    type = FilterType.ASSIGNABLE_TYPE,
                    classes = {StudentService.class, AppConfig.class})
    )
    static class Config {
    }

    @MockBean
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @Test
    void getFullNameById() {

        Student mockedStudent = new Student();
        mockedStudent.setGivenName("Given");
        mockedStudent.setFamilyName("Family");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockedStudent));
        String name = studentService.getFullNameById(1L);
        assertThat(name).as("フルネームが取得できる").isEqualTo(name);
    }

    @Test
    void getStudentList() {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i <= 50; i++) {
            Student student = new Student();
            student.setId(i);
            student.setFamilyName("test");
            student.setGivenName("test" + i);
            studentList.add(student);
        }

        Page<Student> page = new PageImpl<>(studentList);
        when(studentRepository.findAll(Pageable.unpaged())).thenReturn(page);
        Page<Student> students = studentService.getList(Pageable.unpaged());

        int index = 0;
        for (Student student : students) {
            assertThat(student).as("Test for getting student list")
                    .usingRecursiveComparison()
                    .isEqualTo(studentList.get(index));
            index++;
        }

    }

    @Test
    void getStudentProfileById() {
        Course c = new Course();
        c.setId(1L);
        c.setCoursePrefix("TEST");
        c.setCourseNum((short) 101);
        c.setMaxEnroll(50);

        Enrollment en = new Enrollment();
        en.setId(1L);
        en.setCourseId(1L);
        en.setYear((short) 2020);
        en.setStudentId(1L);
        en.setCourse(c);

        Student student = new Student();
        student.setId(1L);
        student.setFamilyName("Test");
        student.setGivenName("Test");
        student.setEnrollments(Arrays.asList(en));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Student fetched = studentService.getStudentById(1L);
        assertThat(fetched).usingRecursiveComparison()
                .isEqualTo(student);

    }

    @Test
    void getStudentProfileNotFound() {
        long id = 1L;
        when(studentRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> studentService.getStudentById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No student record found with id '" + id + "'");

    }

    @Test
    void searchNameWithKeywordTest() {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i <= 50; i++) {
            Student student = new Student();
            student.setId(i);
            student.setFamilyName("test");
            student.setGivenName("test" + i);
            studentList.add(student);
        }

        Page<Student> page = new PageImpl<>(studentList);
        ArgumentCaptor<Specification<Student>> captor = ArgumentCaptor.forClass(Specification.class);
        when(studentRepository.findAll(captor.capture(), any(Pageable.class))).thenReturn(page);

        Page<Student> students = studentService.searchNameWithKeyword("test test2", Pageable.unpaged());

        int index = 0;
        for (Student student : students) {
            assertThat(student).as("Test for getting student list")
                    .usingRecursiveComparison()
                    .isEqualTo(studentList.get(index));
            index++;

        }
        Specification<Student> value = captor.getValue();
        NameSearchSpecification<Student> spec = new NameSearchSpecification<>();

        List<String> keywords = new ArrayList<>(Arrays.asList("test", "test2"));
        Specification<Student> and = keywords.stream().map(spec::containsInName).reduce(Specification.where(null), Specification::and);

        assertThat(value).usingRecursiveComparison().isEqualTo(and);
    }

    @Test
    void loadUserByUsernameTest() throws UsernameNotFoundException {
        Student expected = new Student();
        expected.setStudentId("111111T");
        expected.setGivenName("Test");
        expected.setFamilyName("Test");
        expected.setPassword("test");
        expected.setAdmissionYear(2000);
        expected.setId(1L);
        when(studentRepository.findByStudentId("111111T")).thenReturn(Optional.of(expected));
        UserDetails user = studentService.loadUserByUsername("111111T");
        assertThat(user).isInstanceOf(LoginUser.class)
                .isEqualToComparingOnlyGivenFields(expected, "id");
    }
}
