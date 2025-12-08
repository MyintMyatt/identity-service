package com.oroin.identity_service.features.user.repository;

import com.oroin.identity_service.features.user.entity.OAuthUser;
import com.oroin.identity_service.features.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthUserRepository extends JpaRepository<OAuthUser, String> {

    Optional<OAuthUser> findByUserAndProvider(User user, String provider);
}
