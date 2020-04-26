package jp.voice0726.course_registration.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import jp.voice0726.course_registration.entity.Student;
import jp.voice0726.course_registration.helper.ReplacementCsvDataSetLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners(value = {
        DbUnitTestExecutionListener.class
}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@DbUnitConfiguration(databaseConnection = "dataSource", dataSetLoader = ReplacementCsvDataSetLoader.class)
public class StudentRepositoryTest {

    private static final String DATASET_DIR = "/dbUnit/repository/StudentRepositoryTest/";

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DatabaseSetup(DATASET_DIR)
    public void testFindById() {

        Student expected = new Student();
        expected.setId(1L);
        expected.setPassword("$2a$10$jKNGop3oU3jUlcfWVD6tO..T/gJBG6jFGGj9mHVhIk0EpbfsxGfBO");
        expected.setDepartmentId(1L);
        expected.setStudentId("200001L");
        expected.setGivenName("John");
        expected.setFamilyName("Anderson");
        expected.setAdmissionYear(2015);

        Optional<Student> optionalStudent = studentRepository.findById(1L);
        assert optionalStudent.isPresent();
        Student student = optionalStudent.get();
        assertThat(student).usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt", "enrollments", "department")
                .isEqualTo(expected);
    }
}
