package jp.voice0726.course_registration.repository;

import jp.voice0726.course_registration.entity.Department;
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
