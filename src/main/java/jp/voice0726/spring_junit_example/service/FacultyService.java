package jp.voice0726.spring_junit_example.service;

import jp.voice0726.spring_junit_example.dto.StudentIndexDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FacultyService {
    List<StudentIndexDto> getStudentList(Pageable pageable);
}
