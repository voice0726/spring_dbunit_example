package jp.voice0726.course_registration.dto;

import jp.voice0726.course_registration.entity.Department;
import jp.voice0726.course_registration.entity.Enrollment;
import lombok.Data;

import java.util.Collection;

@Data
public class StudentProfileDto {
    private long id;
    private String givenName;
    private String familyName;
    private int admissionYear;
    private Department department;
    private Collection<Enrollment> enrollments;

    public String getFullName() {
        return givenName + " " + familyName;
    }
}
