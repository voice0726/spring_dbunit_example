package jp.voice0726.spring_junit_example.repository;

import jp.voice0726.spring_junit_example.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>, JpaSpecificationExecutor<Enrollment> {
}
