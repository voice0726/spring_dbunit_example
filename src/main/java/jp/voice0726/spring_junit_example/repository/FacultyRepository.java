package jp.voice0726.spring_junit_example.repository;

import jp.voice0726.spring_junit_example.entity.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long>, JpaSpecificationExecutor<Faculty> {
}
