package jp.voice0726.course_registration.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Course extends AbstractEntity {
    private long id;
    private String coursePrefix;
    private short courseNum;
    private String name;
    private int maxEnroll;
    private long instructorId;
    private Faculty taughtBy;
    private Collection<Enrollment> enrollment;


    private Faculty faculty;
    private Collection<Enrollment> enrollments;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "course_prefix")
    public String getCoursePrefix() {
        return coursePrefix;
    }

    public void setCoursePrefix(String coursePrefix) {
        this.coursePrefix = coursePrefix;
    }

    @Basic
    @Column(name = "course_num")
    public short getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(short courseNum) {
        this.courseNum = courseNum;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "max_enroll")
    public int getMaxEnroll() {
        return maxEnroll;
    }

    public void setMaxEnroll(int maxEnroll) {
        this.maxEnroll = maxEnroll;
    }

    @Basic
    @Column(name = "instructor_id")
    public long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(long instructorId) {
        this.instructorId = instructorId;
    }

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Faculty getTaughtBy() {
        return taughtBy;
    }

    public void setTaughtBy(Faculty faculty) {
        this.taughtBy = faculty;
    }

    @OneToMany(mappedBy = "course")
    public Collection<Enrollment> getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Collection<Enrollment> enrollListsById) {
        this.enrollment = enrollListsById;
    }

    @Basic
    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at")
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @OneToMany(mappedBy = "course")
    public Collection<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Collection<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id &&
                courseNum == course.courseNum &&
                maxEnroll == course.maxEnroll &&
                instructorId == course.instructorId &&
                coursePrefix.equals(course.coursePrefix) &&
                name.equals(course.name) &&
                taughtBy.equals(course.taughtBy) &&
                Objects.equals(enrollment, course.enrollment) &&
                faculty.equals(course.faculty) &&
                Objects.equals(enrollments, course.enrollments) &&
                createdBy == course.createdBy &&
                createdAt.equals(course.createdAt) &&
                updatedBy == course.updatedBy &&
                updatedAt.equals(course.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coursePrefix, courseNum, name, maxEnroll, instructorId, taughtBy, enrollment, faculty, enrollments, createdBy, createdAt, updatedBy, updatedAt);
    }
}
