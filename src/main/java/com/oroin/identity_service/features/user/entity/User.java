package com.oroin.identity_service.features.user.entity;

import com.oroin.identity_service.common.model.entity.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_user")
@Data
@SuperBuilder
@NoArgsConstructor
public class User extends Auditable {

//    @Id
//    @Column(name = "user_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer userId;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(name = "email_address", nullable = false, length = 100)
    private String emailAddress;

    @Column(nullable = false, length = 100)
    private String password;

//    @Column(nullable = false)
    private int rank;

//    @Column(nullable = false)
    private String position;


}
