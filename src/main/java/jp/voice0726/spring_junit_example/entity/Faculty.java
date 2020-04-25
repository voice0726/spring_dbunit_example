package jp.voice0726.spring_junit_example.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Faculty extends AbstractEntity {
    private long id;
    private String givenName;
    private String familyName;
    private Collection<Course> courses;

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

    @OneToMany(mappedBy = "faculty")
    public Collection<Course> getCourses() {
        return courses;
    }

    public void setCourses(Collection<Course> coursesById) {
        this.courses = coursesById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty that = (Faculty) o;
        return id == that.id &&
                givenName.equals(that.givenName) &&
                familyName.equals(that.familyName) &&
                Objects.equals(courses, that.courses) &&
                createdBy == that.createdBy &&
                createdAt.equals(that.createdAt) &&
                updatedBy == that.updatedBy &&
                updatedAt.equals(that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, givenName, familyName, courses, createdBy, createdAt, updatedBy, updatedAt);
    }
}
