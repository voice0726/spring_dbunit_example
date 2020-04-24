package jp.voice0726.spring_junit_example.service.impl;

import jp.voice0726.spring_junit_example.dto.StudentIndexDto;
import jp.voice0726.spring_junit_example.dto.StudentProfileDto;
import jp.voice0726.spring_junit_example.user.LoginUser;
import jp.voice0726.spring_junit_example.entity.Student;
import jp.voice0726.spring_junit_example.repository.StudentRepository;
import jp.voice0726.spring_junit_example.service.StudentService;
import jp.voice0726.spring_junit_example.specification.NameSearchSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;

    @Override
    public String getFullNameById(long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        Student student = optionalStudent.orElseThrow(EntityNotFoundException::new);

        return student.getGivenName() + " " + student.getFamilyName();
    }

    @Override
    public Page<Student> getList(Pageable pageable) {
        return studentRepository.findAll(pageable);

    }

    @Override
    public Page<Student> searchNameWithKeyword(String query, Pageable pageable) {

        final List<String> keywords = splitQuery(query.trim());
        final NameSearchSpecification<Student> nameSearchSpec = new NameSearchSpecification<>();
        final Specification<Student> spec = keywords
                .stream()
                .map(nameSearchSpec::containsInName)
                .reduce(Specification.where(null), Specification::and);

        return studentRepository.findAll(spec, pageable);
    }



    private List<String> splitQuery(String query) {
        List<String> keywords = new ArrayList<>();

        if (query != null && !"".equals(query)) {
            keywords.addAll(Arrays.asList(query.split(" +")));
        }

        return keywords;
    }

    @Override
    public StudentProfileDto getProfileById(long id) {
        Optional<Student> op = studentRepository.findById(id);
        Student student = op.orElseThrow(
                () -> new EntityNotFoundException("No student record found with id '" + id + "'")
        );
        return modelMapper.map(student, StudentProfileDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> op = studentRepository.findByStudentId(username);
        Student student = op.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String name = student.getGivenName() + " " + student.getFamilyName();
        return new LoginUser(student.getId(), name, student.getStudentId(), student.getPassword());
    }
}
