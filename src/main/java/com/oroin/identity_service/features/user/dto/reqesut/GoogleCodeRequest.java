package com.oroin.identity_service.features.user.dto.reqesut;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GoogleCodeRequest {
    @NotBlank(message = "Code cannot be empty")
    String code;
}
