package jp.voice0726.course_registration.dto;

import lombok.Data;

@Data
public class StudentIndexDto {
    private long id;
    private String givenName;
    private String familyName;
    private int admissionYear;

    public String getFullName() {
        return givenName + " " + familyName;
    }
}
