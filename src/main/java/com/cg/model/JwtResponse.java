package com.cg.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
    private String id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String fullName;
    private String role;

    public JwtResponse(String accessToken, String id, String username, String fullName, String role) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role=" + role +
                '}';
    }
}
