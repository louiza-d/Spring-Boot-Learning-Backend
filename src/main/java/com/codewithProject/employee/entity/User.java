package com.codewithProject.employee.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User {

    public static final Integer JWT_REFRESH_TOKEN_DURATION_IN_MILLIS = 24 * 60 * 60 * 1000; // 24 hours

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;

    private String password;

    private String name;

    private String role = "ROLE_ADMIN";

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "REFRESH_TOKEN_CREATED_AT")
    private ZonedDateTime refreshTokenCreatedAt;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        this.refreshTokenCreatedAt = ZonedDateTime.now();
    }

    public boolean isRefreshTokenExpired() {
        return refreshTokenCreatedAt.toInstant().plusMillis(JWT_REFRESH_TOKEN_DURATION_IN_MILLIS)
                .isBefore(ZonedDateTime.now().toInstant());
    }

}
