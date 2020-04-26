package jp.voice0726.course_registration.service.impl;

import jp.voice0726.course_registration.entity.Department;
import jp.voice0726.course_registration.repository.DepartmentRepository;
import jp.voice0726.course_registration.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by akinori on 2020/04/25
 *
 * @author akinori
 */
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Map<Long, String> findAllMap() {
        return departmentRepository.findAll()
                .stream().collect(Collectors.toMap(Department::getId, Department::getName));
    }
}
