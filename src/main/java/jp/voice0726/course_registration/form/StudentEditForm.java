package jp.voice0726.course_registration.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by akinori on 2020/04/25
 *
 * @author akinori
 */
@Data
public class StudentEditForm {
    private Long id;
    @NotBlank(message = "{student.givenName.blank}")
    private String givenName;
    @NotBlank(message = "{student.familyName.blank}")
    private String familyName;
    @NotBlank(message = "{student.studentId.blank}")
    private String studentId;
    @Min(value = 1, message = "{student.departmentId.min}")
    private long departmentId;
    @NotNull(message = "{student.admissionYear.notnull}")
    private Integer admissionYear;
}
