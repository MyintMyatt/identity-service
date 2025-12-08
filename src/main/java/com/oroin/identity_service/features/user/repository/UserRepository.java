package com.oroin.identity_service.features.user.repository;

import com.oroin.identity_service.features.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmailAddress(String email);
}
