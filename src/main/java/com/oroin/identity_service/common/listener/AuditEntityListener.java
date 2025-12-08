package com.oroin.identity_service.common.listener;


import com.oroin.identity_service.common.model.entity.Auditable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class AuditEntityListener {

    @PrePersist
    public void setCreateDate(Object entity) {
        if (entity instanceof Auditable auditable) {
            LocalDateTime now = LocalDateTime.now();
            auditable.setCreateAt(now);
        }
    }

    @PreUpdate
    public void setUpdateDate(Object entity) {
        if (entity instanceof Auditable auditable) {
            LocalDateTime now = LocalDateTime.now();
            auditable.setCreateAt(now);
        }
    }
}
