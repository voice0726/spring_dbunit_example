package jp.voice0726.spring_junit_example.repository;

import jp.voice0726.spring_junit_example.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
