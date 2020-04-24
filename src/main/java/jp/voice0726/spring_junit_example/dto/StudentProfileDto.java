package jp.voice0726.spring_junit_example.dto;

import jp.voice0726.spring_junit_example.entity.Enrollment;
import lombok.Data;

import java.util.Collection;

@Data
public class StudentProfileDto {
    private long id;
    private String givenName;
    private String familyName;
    private int admissionYear;
    private Collection<Enrollment> enrollments;

    public String getFullName() {
        return givenName + " " + familyName;
    }
}
