package jp.voice0726.course_registration.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@MappedSuperclass
public class AbstractEntity {

    protected long createdBy;
    protected Timestamp createdAt;
    protected long updatedBy;
    protected Timestamp updatedAt;

    @Basic
    @Column(name = "created_by")
    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
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
    @Column(name = "updated_by")
    public long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(long updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Basic
    @Column(name = "updated_at")
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void onPrePersist() {
        setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    @PreUpdate
    public void onPreUpdate() {
        setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
    }

}
