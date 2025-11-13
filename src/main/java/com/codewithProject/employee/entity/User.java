package com.codewithProject.employee.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User {

    public static final Integer JWT_REFRESH_TOKEN_DURATION_IN_MILLIS = 24 * 60 * 60 * 1000; 

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

    @Column(nullable = false)
    private  boolean enabled = false;

    @Column(name ="verification_Token")
    private String verificationToken;

    @Column(name = "token_expirarion")
    private  ZonedDateTime tokenExpiration;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setUser(this);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setUser(null);
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        this.refreshTokenCreatedAt = ZonedDateTime.now();
    }

    public boolean isRefreshTokenExpired() {
        return refreshTokenCreatedAt.toInstant().plusMillis(JWT_REFRESH_TOKEN_DURATION_IN_MILLIS)
                .isBefore(ZonedDateTime.now().toInstant());
    }

}
