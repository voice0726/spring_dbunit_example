package jp.voice0726.spring_junit_example.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import jp.voice0726.spring_junit_example.entity.Department;
import jp.voice0726.spring_junit_example.helper.ReplacementCsvDataSetLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by akinori on 2020/04/25
 *
 * @author akinori
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners(value = {
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DbUnitConfiguration(databaseConnection = "dataSource", dataSetLoader = ReplacementCsvDataSetLoader.class)
class DepartmentRepositoryTest {

    private static final String DATASET_DIR = "/dbUnit/repository/DepartmentRepositoryTest/";

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    @DatabaseSetup(DATASET_DIR)
    void findAllTest() {
        List<Department> fetched = departmentRepository.findAll();
        List<String> expectedNames = Arrays.asList(
                "文学部", "理学部", "工学部", "教育学部", "医学部", "国際関係学部", "法学部", "経済学部", "経営学部"
        );

        for (int i = 0; i < expectedNames.size(); i++) {
            assertThat(fetched.get(i)).extracting("name").isEqualTo(expectedNames.get(i));
        }
    }

}
