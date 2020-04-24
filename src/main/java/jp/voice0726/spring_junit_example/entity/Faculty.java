package jp.voice0726.spring_junit_example.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Faculty extends AbstractEntity {
    private long id;
    private String givenName;
    private String familyName;
    private Collection<Course> courses;
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
    @Column(name = "given_name")
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String name) {
        this.givenName = name;
    }

    @Basic
    @Column(name = "family_name")
    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id &&
                Objects.equals(givenName, faculty.givenName) &&
                Objects.equals(familyName, faculty.familyName) &&
                Objects.equals(createdAt, faculty.createdAt) &&
                Objects.equals(updatedAt, faculty.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, givenName, familyName, createdAt, updatedAt);
    }

    @OneToMany(mappedBy = "faculty")
    public Collection<Course> getCourses() {
        return courses;
    }

    public void setCourses(Collection<Course> coursesById) {
        this.courses = coursesById;
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
