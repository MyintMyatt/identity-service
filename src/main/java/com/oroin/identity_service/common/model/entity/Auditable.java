package com.oroin.identity_service.common.model.entity;

import com.oroin.identity_service.common.listener.AuditEntityListener;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;
}
