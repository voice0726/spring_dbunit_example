package jp.voice0726.spring_junit_example.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Student extends AbstractEntity {
    private long id;
    private String studentId;
    private String password;
    private String givenName;
    private String familyName;
    private Collection<Enrollment> enrollments;
    private long departmentId;
    private int admissionYear;
    private Department department;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "student_id")
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "given_name")
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    @Basic
    @Column(name = "family_name")
    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Basic
    @Column(name = "department_id")
    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "admission_year")
    public int getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(int admissionYear) {
        this.admissionYear = admissionYear;
    }

    @Basic
    @Column(name = "created_by")
    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student that = (Student) o;
        return id == that.id &&
                departmentId == that.departmentId &&
                admissionYear == that.admissionYear &&
                studentId.equals(that.studentId) &&
                password.equals(that.password) &&
                givenName.equals(that.givenName) &&
                familyName.equals(that.familyName) &&
                Objects.equals(enrollments, that.enrollments) &&
                department.equals(that.department) &&
                createdBy == that.createdBy &&
                createdAt.equals(that.createdAt) &&
                updatedBy == that.updatedBy &&
                updatedAt.equals(that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentId, password, givenName, familyName, enrollments, departmentId, admissionYear, department, createdBy, createdAt, updatedBy, updatedAt);
    }

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @OneToMany(mappedBy = "student")
    public Collection<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Collection<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
