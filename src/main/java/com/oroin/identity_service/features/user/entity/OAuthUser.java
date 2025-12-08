package com.oroin.identity_service.features.user.entity;

import com.oroin.identity_service.common.model.entity.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_oauth_user")
@NoArgsConstructor
@Data
@SuperBuilder
public class OAuthUser extends Auditable {

    private String provider;
    private String providerUserId;
    private String profilePicture;
    private boolean emailVerified;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
