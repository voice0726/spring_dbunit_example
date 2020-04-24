package jp.voice0726.spring_junit_example.specification;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import jp.voice0726.spring_junit_example.entity.Student;
import jp.voice0726.spring_junit_example.helper.ReplacementCsvDataSetLoader;
import jp.voice0726.spring_junit_example.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DbUnitConfiguration(databaseConnection = "dataSource", dataSetLoader = ReplacementCsvDataSetLoader.class)
class NameSearchSpecificationTest {

    private static final String DATASET_DIR = "/dbUnit/specification/NameSearchSpecificationTest/";

    @Autowired
    private StudentRepository studentRepository;

    @DatabaseSetup(DATASET_DIR)
    @ParameterizedTest
    @CsvSource({"John, 3", "Anderson, 3", "king, 1", "James, 2"})
    void containsInName(String name, int expected) {
        NameSearchSpecification<Student> spec = new NameSearchSpecification<>();
        Page<Student> john = studentRepository.findAll(Specification.where(spec.containsInName(name)), Pageable.unpaged());
        assertThat(john).hasSize(expected);
    }

    @Test
    @DatabaseSetup(DATASET_DIR)
    void containsKeywordsInName() {
        NameSearchSpecification<Student> spec = new NameSearchSpecification<>();
        Page<Student> john = studentRepository.findAll(
                Specification.where(spec.containsInName("John"))
                        .and(spec.containsInName("Anderson")
                        ), Pageable.unpaged());
        assertThat(john).hasSize(1);
    }
}
