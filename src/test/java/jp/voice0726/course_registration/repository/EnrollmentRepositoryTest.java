package jp.voice0726.course_registration.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import jp.voice0726.course_registration.entity.Enrollment;
import jp.voice0726.course_registration.helper.ReplacementCsvDataSetLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DbUnitConfiguration(databaseConnection = "dataSource", dataSetLoader = ReplacementCsvDataSetLoader.class)
public class EnrollmentRepositoryTest {

    private static final String DATASET_DIR = "/dbUnit/repository/EnrollmentRepositoryTest/";

    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Test
    @DatabaseSetup(DATASET_DIR)
    public void testFindById() {
        Enrollment expected = new Enrollment();
        expected.setId(1L);
        expected.setYear((short)2015);
        expected.setStudentId(1L);
        expected.setCourseId(1L);

        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(1L);
        assert optionalEnrollment.isPresent();
        Enrollment enrollment = optionalEnrollment.get();

        assertThat(enrollment).usingRecursiveComparison()
                .ignoringFields("course", "student", "createdAt", "updatedAt")
                .isEqualTo(expected);
    }
}
