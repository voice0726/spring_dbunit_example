package jp.voice0726.spring_junit_example.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "enrollment", schema = "test", catalog = "")
public class Enrollment extends AbstractEntity {
    private long id;
    private short year;
    private long studentId;
    private long courseId;
    private Student student;
    private Course course;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "year")
    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    @Basic
    @Column(name = "student_id")
    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "course_id")
    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return id == that.id &&
                year == that.year &&
                studentId == that.studentId &&
                courseId == that.courseId &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, year, studentId, courseId, createdAt, updatedAt);
    }

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
}
