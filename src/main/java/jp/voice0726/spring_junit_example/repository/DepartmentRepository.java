package jp.voice0726.spring_junit_example.repository;

import jp.voice0726.spring_junit_example.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akinori on 2020/04/25
 *
 * @author akinori
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
