package jp.voice0726.course_registration.service.impl;

import jp.voice0726.course_registration.entity.Department;
import jp.voice0726.course_registration.repository.DepartmentRepository;
import jp.voice0726.course_registration.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by akinori on 2020/04/25
 *
 * @author akinori
 */
@Service
@SpringJUnitConfig(DepartmentServiceImplTest.Config.class)
class DepartmentServiceImplTest {
    @Configuration
    @ComponentScan(
            basePackages = "jp.voice0726.course_registration",
            useDefaultFilters = false,
            includeFilters = @ComponentScan.Filter(
                    type = FilterType.ASSIGNABLE_TYPE,
                    classes = {DepartmentService.class})
    )
    static class Config {
    }

    @MockBean
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentService departmentService;

    @Test
    void findAllMap() {

        List<Department> returned = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Department dept = new Department();
            dept.setId(i);
            dept.setName("Test" + (i));
            returned.add(dept);
        }

        when(departmentRepository.findAll()).thenReturn(returned);
        Map<Long, String> map = departmentService.findAllMap();

        for (int i = 1; i < 10; i++) {
            assertThat(map).extractingByKey((long) i).isEqualTo("Test" + i);
        }
    }
}
