package com.board.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
@Getter
public class AuditingFields {
    @CreatedDate
    @Column(name = "created_at")
    protected LocalDateTime createdAt;
    
    @CreatedBy
    @Column(nullable = false, updatable = false, length = 20)
    protected String createdBy = "system";	// 기본값 설정
    
    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;
    
    @LastModifiedBy
    @Column(nullable = false, updatable = false, length = 20)
    protected String updatedBy = "system";	// 기본값 설정
}
