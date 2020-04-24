package jp.voice0726.spring_junit_example.entity;

import javax.persistence.*;
import java.sql.Timestamp;
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
    private long createdBy;
    private long updatedBy;

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

    @Basic
    @Column(name = "created_by")
    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "updated_by")
    public long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(long updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id == admin.id &&
                createdBy == admin.createdBy &&
                updatedBy == admin.updatedBy &&
                Objects.equals(name, admin.name) &&
                Objects.equals(createdAt, admin.createdAt) &&
                Objects.equals(updatedAt, admin.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdBy, createdAt, updatedBy, updatedAt);
    }


}
