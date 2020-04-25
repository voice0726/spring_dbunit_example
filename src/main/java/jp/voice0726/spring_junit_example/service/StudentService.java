package jp.voice0726.spring_junit_example.service;

import jp.voice0726.spring_junit_example.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public interface StudentService extends UserDetailsService {
    String getFullNameById(long id);

    Page<Student> getList(Pageable pageable);

    Page<Student> searchNameWithKeyword(String query, Pageable pageable);

    Student getStudentById(long id);

    @Transactional
    Student addStudent(Student student);

    @Transactional
    Student updateStudent(Student student);
}
