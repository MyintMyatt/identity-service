package com.oroin.identity_service.features.user.dto.reqesut;

import com.oroin.identity_service.common.model.request_dto_gp.Create;
import com.oroin.identity_service.common.model.request_dto_gp.Update;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {

    @Null(groups = Create.class)
    @NotBlank(groups = Update.class, message = "userid must not be null or empty")
    private String userid;

    @NotBlank(message = "username must not be null or empty")
    @Min(value = 4, message = "user name length must be greater than or equal 4")
    private String username;

    @NotBlank(message = "email must not be null or empty")
    @Email(message = "invalid email address format")
    private String emailAddress;

    @NotBlank(message = "password must not be null or empty")
    @Min(value = 6, message = "password length must be greater than or equal 6")
    private String password;

    @NotNull(message = "rank must not be null")
    private Integer rank;

    @NotBlank(message = "position must not be empty or null")
    private String position;
}
