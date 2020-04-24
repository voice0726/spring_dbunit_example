package jp.voice0726.spring_junit_example.service;

import jp.voice0726.spring_junit_example.dto.StudentProfileDto;
import jp.voice0726.spring_junit_example.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface StudentService extends UserDetailsService {
    String getFullNameById(long id);

    Page<Student> getList(Pageable pageable);

    Page<Student> searchNameWithKeyword(String query, Pageable pageable);

    StudentProfileDto getProfileById(long id);
}
