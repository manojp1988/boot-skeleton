package com.example.boot.data.entity;

import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @Version
    protected Long version;

    @CreatedDate
    protected LocalDateTime createAt;

    @CreatedBy
    protected String createdBy;

    @LastModifiedDate
    protected LocalDateTime modifiedAt;

    @LastModifiedBy
    protected String modifiedBy;

}
