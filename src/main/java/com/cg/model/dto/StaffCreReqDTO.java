package com.cg.model.dto;

import com.cg.model.Role;
import com.cg.model.Staff;
import com.cg.model.User;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class StaffCreReqDTO {

    @NotBlank(message = "The username is required")
    @Email(message = "The email address is invalid")
    @Size(min = 5, max = 50, message = "The length of email must be between 5 and 50 characters")
    private String username;

    @NotBlank(message = "The password is required")
    private String password;

    @NotBlank(message = "The full name is required")
    private String fullName;

    private String phone;

    private String roleCode;

    public Staff toStaff(User user) {
        return new Staff()
                .setUser(user)
                .setFullName(fullName)
                .setPhone(phone)
                ;
    }

    public User toUser(String password, Role role) {
        return new User()
                .setUsername(username)
                .setPassword(password)
                .setRole(role)
                ;
    }

}
