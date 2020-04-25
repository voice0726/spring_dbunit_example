package jp.voice0726.spring_junit_example.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by akinori on 2020/04/23
 *
 * @author akinori
 */
@Entity
@EqualsAndHashCode(callSuper = true)
public class Department extends AbstractEntity {
    private long id;
    private String name;
    private Collection<Student> students;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "department")
    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }
}
