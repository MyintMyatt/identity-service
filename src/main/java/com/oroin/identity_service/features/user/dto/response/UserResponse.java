package com.oroin.identity_service.features.user.dto.response;

public record UserResponse(
        String username,
        String emailAddress,
        Integer rank,
        String position
) {
}
