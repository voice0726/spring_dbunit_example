package jp.voice0726.course_registration.service;

import jp.voice0726.course_registration.dto.StudentIndexDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FacultyService {
    List<StudentIndexDto> getStudentList(Pageable pageable);
}
