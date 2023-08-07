package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginReqDTO {

    @NotBlank(message = "The email is required")
    private String username;

    @NotBlank(message = "The password is required")
    private String password;
}
