package jp.voice0726.course_registration.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by akinori on 2020/04/23
 *
 * @author akinori
 */
@Entity
@Table(name = "admin", schema = "test")
public class Admin extends AbstractEntity {
    private long id;
    private String name;
    private String username;
    private String password;

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

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id == admin.id &&
                name.equals(admin.name) &&
                username.equals(admin.username) &&
                password.equals(admin.password) &&
                createdBy == admin.createdBy &&
                createdAt.equals(admin.createdAt) &&
                updatedBy == admin.updatedBy &&
                updatedAt.equals(admin.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, password, createdBy, createdAt, updatedBy, updatedAt);
    }
}
